package com.burakozdemir.cleanzy.cleaner.controller;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CleanerController {
    ResponseEntity<ApiSuccessResponse<List<CleanerResponseDTO>>> getCleanerList(ServiceType service);
    ResponseEntity<ApiSuccessResponse<CleanerResponseDTO>> getCleanerDetailsByID(Long cleanerID);
}
