package com.burakozdemir.cleanzy.customer.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ApiSuccessResponse<List<CustomerResponseDTO>> fetchCustomerList() {
        return null;
    }

    @Override
    public ApiSuccessResponse<CustomerResponseDTO> fetchCustomerDetails(Long customerID) {
        return null;
    }
}
