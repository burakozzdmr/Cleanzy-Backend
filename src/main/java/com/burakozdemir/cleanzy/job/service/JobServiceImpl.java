package com.burakozdemir.cleanzy.job.service;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerSummaryDTO;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.dto.CustomerSummaryDTO;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import com.burakozdemir.cleanzy.job.entity.Job;
import com.burakozdemir.cleanzy.job.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<JobResponseDTO>> fetchAllJobs() {
        List<JobResponseDTO> jobs = jobRepository.findAll()
                .stream()
                .map(this::toJobResponseDTO)
                .toList();

        return ApiSuccessResponse.of(jobs, jobs.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<JobResponseDTO> fetchJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(ErrorType.JOB_NOT_FOUND));

        return ApiSuccessResponse.of(toJobResponseDTO(job));
    }

    @Override
    public ApiSuccessResponse<JobResponseDTO> addJob(JobRequestDTO jobRequest) {
        return null;
    }

    @Override
    public ApiSuccessResponse<JobResponseDTO> updateJob(JobRequestDTO jobRequest) {
        return null;
    }

    @Override
    public ApiSuccessResponse<JobResponseDTO> deleteJob(JobRequestDTO jobRequest) {
        return null;
    }

    private JobResponseDTO toJobResponseDTO(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setAddress(job.getAddress());
        dto.setCity(job.getCity());
        dto.setPrice(job.getPrice());
        dto.setScheduledAt(job.getScheduledAt());
        dto.setStatus(job.getStatus());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setUpdatedAt(job.getUpdatedAt());

        Customer customer = job.getCustomer();
        if (customer != null) {
            dto.setCustomer(new CustomerSummaryDTO(
                    customer.getId(),
                    customer.getUser().getFullName(),
                    customer.getCurrentLocation(),
                    customer.getRating()
            ));
        }

        Cleaner cleaner = job.getAssignedCleaner();
        if (cleaner != null) {
            dto.setAssignedCleaner(new CleanerSummaryDTO(
                    cleaner.getId(),
                    cleaner.getUser().getFullName(),
                    cleaner.getCurrentLocation(),
                    cleaner.getRating(),
                    cleaner.getIbanNumber(),
                    cleaner.getHourlyRate(),
                    cleaner.getServices(),
                    cleaner.getBiography()
            ));
        }

        return dto;
    }
}
