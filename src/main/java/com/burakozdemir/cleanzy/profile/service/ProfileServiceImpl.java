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
import com.burakozdemir.cleanzy.profile.dto.CleanerProfileDTO;
import com.burakozdemir.cleanzy.profile.dto.CustomerProfileDTO;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import com.burakozdemir.cleanzy.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    .map(customer -> ApiSuccessResponse.<ProfileDTO>of(toCustomerProfileDTO(user, customer)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CUSTOMER_NOT_FOUND));

            case CLEANER -> cleanerRepository.findByUser(user)
                    .map(cleaner -> ApiSuccessResponse.<ProfileDTO>of(toCleanerProfileDTO(user, cleaner)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CLEANER_NOT_FOUND));
        };
    }

    private CustomerProfileDTO toCustomerProfileDTO(User user, Customer customer) {
        long totalJobs = jobRepository.countByCustomer(customer);

        return CustomerProfileDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .customerId(customer.getId())
                .currentLocation(customer.getCurrentLocation())
                .profilePhotoURL(customer.getProfilePhotoURL())
                .verified(customer.isVerified())
                .totalJobs((int) totalJobs)
                .build();
    }

    private CleanerProfileDTO toCleanerProfileDTO(User user, Cleaner cleaner) {
        long totalJobsCompleted = jobRepository.countByAssignedCleaner_IdAndStatus(
                cleaner.getId(), JobStatusType.COMPLETED
        );

        return CleanerProfileDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .cleanerId(cleaner.getId())
                .biography(cleaner.getBiography())
                .hourlyRate(cleaner.getHourlyRate())
                .rating(cleaner.getRating())
                .totalReviews(cleaner.getTotalReviews())
                .services(cleaner.getServices())
                .schedule(cleaner.getSchedule())
                .serviceArea(cleaner.getServiceArea())
                .profilePhotoURL(cleaner.getProfilePhotoURL())
                .verified(cleaner.isVerified())
                .available(cleaner.isAvailable())
                .totalJobsCompleted(totalJobsCompleted)
                .build();
    }
}
