package com.burakozdemir.cleanzy.customer.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerResponseDTO;
import com.burakozdemir.cleanzy.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/customers")
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ApiSuccessResponse<List<CustomerResponseDTO>>> getCustomerList() {
        return ResponseEntity
                .ok(customerService.fetchCustomerList());
    }

    @Override
    @GetMapping("/{customerID}")
    public ResponseEntity<ApiSuccessResponse<CustomerResponseDTO>> getCustomerDetailsByID(@PathVariable Long customerID) {
        return ResponseEntity
                .ok(customerService.fetchCustomerDetails(customerID));
    }
}
