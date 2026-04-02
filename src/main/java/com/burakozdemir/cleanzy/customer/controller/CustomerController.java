package com.burakozdemir.cleanzy.customer.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerController {
    public ResponseEntity<ApiSuccessResponse<List<CustomerResponseDTO>>> getCustomerList();
    public ResponseEntity<ApiSuccessResponse<CustomerResponseDTO>> getCustomerDetailsByID(Long customerID);
}
