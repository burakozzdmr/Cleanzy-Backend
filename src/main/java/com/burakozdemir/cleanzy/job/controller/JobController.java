package com.burakozdemir.cleanzy.job.controller;


import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobController {
    public ResponseEntity<ApiSuccessResponse<List<JobResponseDTO>>> getAllJobs();
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> getJobById(Long jobID);
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> addJob(JobRequestDTO jobRequest);
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> updateJob(JobRequestDTO jobRequest);
    public ResponseEntity<ApiSuccessResponse<JobResponseDTO>> deleteJob(JobRequestDTO jobRequest);
}
