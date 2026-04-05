package com.burakozdemir.cleanzy.cleaner.dto;

import com.burakozdemir.cleanzy.auth.dto.UserSummaryDTO;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanerResponseDTO implements ProfileDTO {
    private Long id;
    private UserSummaryDTO user;
    private String biography;
    private String currentLocation;
    private Double rating;
    private Integer totalReviews;
    private BigDecimal hourlyRate;
    private Set<String> serviceArea;
    private String profilePhotoURL;
    private Map<DayOfWeek, String> schedule;
    private Set<ServiceType> services;
    private boolean isVerified;
    private boolean isAvailable;
}
