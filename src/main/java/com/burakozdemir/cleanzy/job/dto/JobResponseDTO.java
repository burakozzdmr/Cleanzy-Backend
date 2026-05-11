package com.burakozdemir.cleanzy.job.dto;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerSummaryDTO;
import com.burakozdemir.cleanzy.common.util.HouseSizeType;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.dto.CustomerSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDTO {
    private Long id;
    private CustomerSummaryDTO customer;
    private CleanerSummaryDTO assignedCleaner;
    private String title;
    private String description;
    private String address;
    private String city;
    private String timeSlot;
    private HouseSizeType houseSize;
    private List<String> extraServices;
    private String notes;
    private Double price;
    private LocalDateTime scheduledAt;
    private JobStatusType status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
