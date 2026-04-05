package com.burakozdemir.cleanzy.job.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import com.burakozdemir.cleanzy.job.entity.Job;
import com.burakozdemir.cleanzy.job.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/jobs")
public class JobControllerImpl implements JobController {
    private final JobService jobService;

    JobControllerImpl(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getAllJobs() {
        return ResponseEntity
                .ok(jobService.fetchAllJobs());
    }

    @Override
    @GetMapping("/{jobID}")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> getJobById(@PathVariable Long jobId) {
        return ResponseEntity
                .ok(jobService.fetchJobById(jobId));
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> addJob(@RequestBody JobRequestDTO jobRequest) {
        return null;
    }

    @Override
    @PatchMapping("/")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> updateJob(@RequestBody JobRequestDTO jobRequest) {
        return null;
    }

    @Override
    @DeleteMapping("/")
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> deleteJob(JobRequestDTO jobRequest) {
        return null;
    }
}
