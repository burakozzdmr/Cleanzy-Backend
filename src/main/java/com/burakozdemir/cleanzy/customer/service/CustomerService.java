package com.burakozdemir.cleanzy.customer.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    public ApiSuccessResponse<List<CustomerResponseDTO>> fetchCustomerList();
    public ApiSuccessResponse<CustomerResponseDTO> fetchCustomerDetails(Long customerID);
}
