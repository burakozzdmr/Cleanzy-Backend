package com.burakozdemir.cleanzy.cleaner.dto;

import com.burakozdemir.cleanzy.common.util.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CleanerSummaryDTO {
    private Long id;
    private String fullName;
    private String currentLocation;
    private Double rating;
    private String ibanNumber;
    private BigDecimal hourlyRate;
    private Set<ServiceType> services;
    private String biography;
}
