package com.burakozdemir.cleanzy.customer.service;

import com.burakozdemir.cleanzy.auth.dto.UserSummaryDTO;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<CustomerResponseDTO>> fetchCustomerList() {
        List<CustomerResponseDTO> customerListDto = customerRepository.findAll()
                .stream()
                .map(this::toCustomerResponseDTO)
                .toList();

        return ApiSuccessResponse.of(customerListDto, customerListDto.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<CustomerResponseDTO> fetchCustomerDetails(Long customerID) {
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(() -> new BusinessException(ErrorType.CUSTOMER_NOT_FOUND));

        return ApiSuccessResponse.of(toCustomerResponseDTO(customer));
    }

    private CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        BeanUtils.copyProperties(customer, dto);

        if (customer.getUser() != null) {
            UserSummaryDTO userSummary = new UserSummaryDTO();
            BeanUtils.copyProperties(customer.getUser(), userSummary);
            dto.setUser(userSummary);
        }

        return dto;
    }
}
