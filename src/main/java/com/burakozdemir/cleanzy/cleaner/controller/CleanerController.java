package com.burakozdemir.cleanzy.cleaner.controller;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CleanerController {
    public ResponseEntity<ApiSuccessResponse<List<CleanerResponseDTO>>> getCleanerList();
    public ResponseEntity<ApiSuccessResponse<CleanerResponseDTO>> getCleanerDetailsByID(Long cleanerID);
}
