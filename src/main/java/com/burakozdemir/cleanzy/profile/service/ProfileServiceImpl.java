package com.burakozdemir.cleanzy.profile.service;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.job.repository.JobRepository;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import com.burakozdemir.cleanzy.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;
    private final JobRepository jobRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<ProfileDTO> fetchProfile(Long userId) {
        User user = profileRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        return switch (user.getRole()) {
            case CUSTOMER -> customerRepository.findByUser(user)
                    .map(customer -> ApiSuccessResponse.of(toCustomerProfileDTO(user, customer)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CUSTOMER_NOT_FOUND));

            case CLEANER -> cleanerRepository.findByUser(user)
                    .map(cleaner -> ApiSuccessResponse.of(toCleanerProfileDTO(user, cleaner)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CLEANER_NOT_FOUND));
        };
    }

    private ProfileDTO toCustomerProfileDTO(User user, Customer customer) {
        long totalJobs = jobRepository.countByCustomer(customer);

        return ProfileDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .profilePhotoURL(customer.getProfilePhotoURL())
                .verified(customer.isVerified())
                .createdAt(user.getCreatedAt())
                .currentLocation(customer.getCurrentLocation())
                .customerId(customer.getId())
                .totalJobs((int) totalJobs)
                .build();
    }

    private ProfileDTO toCleanerProfileDTO(User user, Cleaner cleaner) {
        long totalJobsCompleted = jobRepository.countByAssignedCleaner_IdAndStatus(
                cleaner.getId(), JobStatusType.COMPLETED
        );

        List<String> serviceNames = cleaner.getServices() == null
                ? List.of()
                : cleaner.getServices().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList());

        List<String> serviceAreaList = cleaner.getServiceArea() == null
                ? List.of()
                : new ArrayList<>(cleaner.getServiceArea());

        Map<String, String> scheduleMap = new HashMap<>();
        if (cleaner.getSchedule() != null) {
            cleaner.getSchedule().forEach((day, hours) -> scheduleMap.put(day.name(), hours));
        }

        return ProfileDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .profilePhotoURL(cleaner.getProfilePhotoURL())
                .verified(cleaner.isVerified())
                .createdAt(user.getCreatedAt())
                .currentLocation(cleaner.getCurrentLocation())
                .cleanerId(cleaner.getId())
                .biography(cleaner.getBiography())
                .hourlyRate(cleaner.getHourlyRate() != null ? cleaner.getHourlyRate().doubleValue() : null)
                .rating(cleaner.getRating())
                .totalReviews(cleaner.getTotalReviews())
                .services(serviceNames)
                .schedule(scheduleMap)
                .serviceArea(serviceAreaList)
                .available(cleaner.isAvailable())
                .totalJobsCompleted((int) totalJobsCompleted)
                .build();
    }
}
