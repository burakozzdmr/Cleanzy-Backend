package com.burakozdemir.cleanzy.job.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import com.burakozdemir.cleanzy.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/jobs")
@RequiredArgsConstructor
@Tag(name = "Jobs", description = "Job / appointment management")
@SecurityRequirement(name = "bearerAuth")
public class JobControllerImpl implements JobController {

    private final JobService jobService;

    @Override
    @GetMapping("/")
    @Operation(summary = "Get all jobs")
    public ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getAllJobs() {
        return ResponseEntity.ok(jobService.fetchAllJobs());
    }

    @Override
    @GetMapping("/{jobId}")
    @Operation(summary = "Get job by ID")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> getJobById(
            @PathVariable("jobId") Long jobId
    ) {
        return ResponseEntity.ok(jobService.fetchJobById(jobId));
    }

    @Override
    @PostMapping("/")
    @Operation(summary = "Create a new job")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> addJob(
            @Valid @RequestBody JobRequestDTO jobRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.addJob(jobRequest));
    }

    @Override
    @GetMapping("/my")
    @Operation(summary = "Get jobs for the authenticated user (CUSTOMER or CLEANER)")
    public ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getMyJobs(
            @RequestParam Long userId,
            @RequestParam String role,
            @RequestParam(required = false) JobStatusType status
    ) {
        return ResponseEntity.ok(jobService.fetchMyJobs(userId, role, status));
    }

    @Override
    @DeleteMapping("/{jobId}")
    @Operation(summary = "Delete a job (only OPEN or CANCELLED, only by the owning customer)")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteJob(
            @PathVariable Long jobId,
            @RequestParam Long requestingUserId
    ) {
        return ResponseEntity.ok(jobService.deleteJobById(jobId, requestingUserId));
    }
}
