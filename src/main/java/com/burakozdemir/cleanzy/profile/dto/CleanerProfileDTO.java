package com.burakozdemir.cleanzy.profile.dto;

import com.burakozdemir.cleanzy.common.util.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CleanerProfileDTO implements ProfileDTO {

    // Base fields
    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    // Cleaner-specific fields
    private Long cleanerId;
    private String biography;
    private BigDecimal hourlyRate;
    private Double rating;
    private Integer totalReviews;
    private Set<ServiceType> services;
    private Map<DayOfWeek, String> schedule;
    private Set<String> serviceArea;
    private String profilePhotoURL;
    private Boolean verified;
    private Boolean available;
    private Long totalJobsCompleted;
}
