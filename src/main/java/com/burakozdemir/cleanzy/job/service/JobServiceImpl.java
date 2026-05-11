package com.burakozdemir.cleanzy.job.service;

import com.burakozdemir.cleanzy.auth.entity.Role;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.cleaner.dto.CleanerSummaryDTO;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.dto.CustomerSummaryDTO;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.job.dto.JobRequestDTO;
import com.burakozdemir.cleanzy.job.dto.JobResponseDTO;
import com.burakozdemir.cleanzy.job.entity.Job;
import com.burakozdemir.cleanzy.job.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final CleanerRepository cleanerRepository;
    private final AuthRepository authRepository;

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
    @Transactional
    public ApiSuccessResponse<JobResponseDTO> addJob(JobRequestDTO request) {
        // customerId field carries the User.id (from JWT auth response),
        // not the Customer table PK — resolve via user FK.
        Customer customer = customerRepository.findByUser_Id(request.getCustomerId())
                .orElseThrow(() -> new BusinessException(ErrorType.CUSTOMER_NOT_FOUND));

        Cleaner cleaner = cleanerRepository.findById(request.getCleanerId())
                .orElseThrow(() -> new BusinessException(ErrorType.CLEANER_NOT_FOUND));

        LocalDateTime scheduledAt = resolveScheduledAt(request);
        String timeSlot = resolveTimeSlot(request);

        Job job = new Job();
        job.setCustomer(customer);
        job.setAssignedCleaner(cleaner);
        job.setAddress(request.getAddress());
        job.setCity(request.getCity() != null ? request.getCity() : "");
        job.setScheduledAt(scheduledAt);
        job.setTimeSlot(timeSlot);
        job.setHouseSize(request.getHouseSize());
        job.setExtraServices(request.getExtraServices());
        job.setPrice(request.getTotalPrice() != null ? request.getTotalPrice() : 0.0);
        job.setNotes(request.getNotes());
        job.setStatus(JobStatusType.OPEN);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        Job saved = jobRepository.save(job);
        return ApiSuccessResponse.of(toJobResponseDTO(saved));
    }

    @Override
    @Transactional
    public ApiSuccessResponse<JobResponseDTO> updateJob(JobRequestDTO request) {
        return null;
    }

    @Override
    @Transactional
    public ApiSuccessResponse<Void> deleteJobById(Long jobId, Long requestingUserId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(ErrorType.JOB_NOT_FOUND));

        Long ownerUserId = job.getCustomer().getUser().getId();
        if (!ownerUserId.equals(requestingUserId)) {
            throw new BusinessException(ErrorType.UNAUTHORIZED_ACCESS);
        }

        if (job.getStatus() != JobStatusType.OPEN && job.getStatus() != JobStatusType.CANCELLED) {
            throw new BusinessException(ErrorType.JOB_CANNOT_BE_DELETED);
        }

        jobRepository.delete(job);
        return ApiSuccessResponse.of(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<JobResponseDTO>> fetchMyJobs(Long userId, String role, JobStatusType status) {
        List<Job> jobs;

        Role userRole;
        try {
            userRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorType.INVALID_ROLE);
        }

        if (userRole == Role.CUSTOMER) {
            jobs = (status != null)
                    ? jobRepository.findByCustomer_User_IdAndStatus(userId, status)
                    : jobRepository.findByCustomer_User_Id(userId);
        } else {
            jobs = (status != null)
                    ? jobRepository.findByAssignedCleaner_User_IdAndStatus(userId, status)
                    : jobRepository.findByAssignedCleaner_User_Id(userId);
        }

        List<JobResponseDTO> dtos = jobs.stream().map(this::toJobResponseDTO).toList();
        return ApiSuccessResponse.of(dtos, dtos.size());
    }

    // ── Scheduling helpers ────────────────────────────────────────────────────

    /**
     * Accepts either a pre-built {@code scheduledAt} (LocalDateTime) or the iOS pair
     * {@code scheduledDate} ("yyyy-MM-dd") + {@code scheduledTime} ("HH:mm").
     */
    private LocalDateTime resolveScheduledAt(com.burakozdemir.cleanzy.job.dto.JobRequestDTO req) {
        if (req.getScheduledAt() != null) {
            return req.getScheduledAt();
        }
        if (req.getScheduledDate() != null && req.getScheduledTime() != null) {
            try {
                LocalDate date = LocalDate.parse(req.getScheduledDate());
                LocalTime time = LocalTime.parse(req.getScheduledTime());
                return LocalDateTime.of(date, time);
            } catch (DateTimeParseException ignored) { }
        }
        return LocalDateTime.now();
    }

    /**
     * Uses the explicit {@code timeSlot} field when present; falls back to
     * {@code scheduledTime} (the iOS-style "HH:mm" string).
     */
    private String resolveTimeSlot(com.burakozdemir.cleanzy.job.dto.JobRequestDTO req) {
        if (req.getTimeSlot() != null && !req.getTimeSlot().isBlank()) {
            return req.getTimeSlot();
        }
        return req.getScheduledTime() != null ? req.getScheduledTime() : "";
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private JobResponseDTO toJobResponseDTO(Job job) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setAddress(job.getAddress());
        dto.setCity(job.getCity());
        dto.setTimeSlot(job.getTimeSlot());
        dto.setHouseSize(job.getHouseSize());
        dto.setExtraServices(job.getExtraServices());
        dto.setNotes(job.getNotes());
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
