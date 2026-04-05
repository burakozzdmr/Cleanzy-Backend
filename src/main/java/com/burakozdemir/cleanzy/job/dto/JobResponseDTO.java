package com.burakozdemir.cleanzy.job.dto;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerSummaryDTO;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.dto.CustomerSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDTO {
    private Long id;
    private CustomerSummaryDTO customer;
    private String title;
    private String description;
    private String address;
    private String city;
    private Double price;
    private LocalDateTime scheduledAt;
    private JobStatusType status;
    private CleanerSummaryDTO assignedCleaner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
