package com.burakozdemir.cleanzy.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileDTO implements ProfileDTO {

    // Base fields
    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    // Customer-specific fields
    private Long customerId;
    private String currentLocation;
    private String profilePhotoURL;
    private Boolean verified;
    private Integer totalJobs;
}
