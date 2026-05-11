package com.burakozdemir.cleanzy.job.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;

import java.util.List;

public interface JobService {

    ApiSuccessResponse<List<JobResponseDTO>> fetchAllJobs();

    ApiSuccessResponse<JobResponseDTO> fetchJobById(Long jobId);

    ApiSuccessResponse<JobResponseDTO> addJob(JobRequestDTO jobRequest);

    ApiSuccessResponse<JobResponseDTO> updateJob(JobRequestDTO jobRequest);

    ApiSuccessResponse<Void> deleteJobById(Long jobId, Long requestingUserId);

    ApiSuccessResponse<List<JobResponseDTO>> fetchMyJobs(Long userId, String role, JobStatusType status);
}
