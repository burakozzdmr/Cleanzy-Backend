package com.burakozdemir.cleanzy.profile.service;

import com.burakozdemir.cleanzy.auth.dto.UserSummaryDTO;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import com.burakozdemir.cleanzy.profile.repository.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;

    ProfileServiceImpl(
            ProfileRepository profileRepository,
            CustomerRepository customerRepository,
            CleanerRepository cleanerRepository
    ) {
        this.profileRepository = profileRepository;
        this.customerRepository = customerRepository;
        this.cleanerRepository = cleanerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<ProfileDTO> fetchProfile(Long userId) {
        User user = profileRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        return switch (user.getRole()) {
            case CUSTOMER -> customerRepository.findByUser(user)
                    .map(customer -> ApiSuccessResponse.<ProfileDTO>of(toCustomerResponseDTO(customer)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CUSTOMER_NOT_FOUND));

            case CLEANER -> cleanerRepository.findByUser(user)
                    .map(cleaner -> ApiSuccessResponse.<ProfileDTO>of(toCleanerResponseDTO(cleaner)))
                    .orElseThrow(() -> new BusinessException(ErrorType.CLEANER_NOT_FOUND));
        };
    }

    private CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        BeanUtils.copyProperties(customer, dto);

        UserSummaryDTO userSummary = new UserSummaryDTO();
        BeanUtils.copyProperties(customer.getUser(), userSummary);
        dto.setUser(userSummary);

        return dto;
    }

    private CleanerResponseDTO toCleanerResponseDTO(Cleaner cleaner) {
        CleanerResponseDTO dto = new CleanerResponseDTO();
        BeanUtils.copyProperties(cleaner, dto);

        UserSummaryDTO userSummary = new UserSummaryDTO();
        BeanUtils.copyProperties(cleaner.getUser(), userSummary);
        dto.setUser(userSummary);

        return dto;
    }
}
