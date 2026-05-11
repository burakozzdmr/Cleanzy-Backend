package com.burakozdemir.cleanzy.cleaner.controller;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.cleaner.service.CleanerService;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/cleaners")
@Tag(name = "Cleaners", description = "Cleaner listing and detail")
@SecurityRequirement(name = "bearerAuth")
public class CleanerControllerImpl implements CleanerController {

    private final CleanerService cleanerService;

    CleanerControllerImpl(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @Override
    @GetMapping("/")
    @Operation(summary = "List all cleaners, optionally filtered by service type")
    public ResponseEntity<ApiSuccessResponse<List<CleanerResponseDTO>>> getCleanerList(
            @Parameter(description = "Filter by offered service type")
            @RequestParam(required = false) ServiceType service
    ) {
        return ResponseEntity.ok(cleanerService.fetchCleanerList(service));
    }

    @Override
    @GetMapping("/{cleanerID}")
    @Operation(summary = "Get cleaner details by ID")
    public ResponseEntity<ApiSuccessResponse<CleanerResponseDTO>> getCleanerDetailsByID(
            @PathVariable Long cleanerID
    ) {
        return ResponseEntity.ok(cleanerService.fetchCleanerDetailsByID(cleanerID));
    }
}
