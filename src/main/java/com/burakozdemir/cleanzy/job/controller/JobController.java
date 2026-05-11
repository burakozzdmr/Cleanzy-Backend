package com.burakozdemir.cleanzy.job.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobController {

    ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getAllJobs();

    ResponseEntity<ApiSuccessResponse<JobResponseDTO>> getJobById(Long jobId);

    ResponseEntity<ApiSuccessResponse<JobResponseDTO>> addJob(JobRequestDTO jobRequest);

    ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getMyJobs(Long userId, String role, JobStatusType status);

    ResponseEntity<ApiSuccessResponse<Void>> deleteJob(Long jobId, Long requestingUserId);
}
