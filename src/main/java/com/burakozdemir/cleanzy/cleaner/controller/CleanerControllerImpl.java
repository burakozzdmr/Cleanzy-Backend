package com.burakozdemir.cleanzy.cleaner.controller;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.cleaner.service.CleanerService;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/cleaners")
public class CleanerControllerImpl implements CleanerController {
    private final CleanerService cleanerService;

    CleanerControllerImpl(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ApiSuccessResponse<List<CleanerResponseDTO>>> getCleanerList() {
        return null;
    }

    @Override
    @GetMapping("/{cleanerID}")
    public ResponseEntity<ApiSuccessResponse<CleanerResponseDTO>> getCleanerDetails(@PathVariable Long cleanerID) {
        return null;
    }
}
