package com.burakozdemir.cleanzy.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User profile. Fields that do not apply to the user's role are returned as null.")
public class ProfileDTO {

    // ── Base fields (always present for both roles) ───────────────────────────

    @Schema(description = "Unique user ID", example = "1")
    private Long userId;

    @Schema(description = "Full name of the user", example = "Jane Doe")
    private String fullName;

    @Schema(description = "Email address", example = "jane@example.com")
    private String email;

    @Schema(description = "User role", example = "CUSTOMER", allowableValues = {"CUSTOMER", "CLEANER"})
    private String role;

    @Schema(description = "Profile photo URL")
    private String profilePhotoURL;

    @Schema(description = "Whether the account has been verified")
    private Boolean verified;

    @Schema(description = "Account creation timestamp")
    private LocalDateTime createdAt;

    // ── Shared optional field populated for both roles ────────────────────────

    @Schema(description = "Current location of the user (present for both CUSTOMER and CLEANER)")
    private String currentLocation;

    // ── CUSTOMER-only fields (null when role = CLEANER) ───────────────────────

    @Schema(description = "Customer record ID (CUSTOMER only)", example = "5")
    private Long customerId;

    @Schema(description = "Total number of jobs created by this customer (CUSTOMER only)", example = "12")
    private Integer totalJobs;

    // ── CLEANER-only fields (null when role = CUSTOMER) ───────────────────────

    @Schema(description = "Cleaner record ID (CLEANER only)", example = "3")
    private Long cleanerId;

    @Schema(description = "Short biography of the cleaner (CLEANER only)")
    private String biography;

    @Schema(description = "Hourly rate charged by the cleaner (CLEANER only)", example = "25.0")
    private Double hourlyRate;

    @Schema(description = "Average rating of the cleaner (CLEANER only)", example = "4.8")
    private Double rating;

    @Schema(description = "Total number of reviews received (CLEANER only)", example = "37")
    private Integer totalReviews;

    @Schema(description = "List of offered service type names (CLEANER only)")
    private List<String> services;

    @Schema(description = "Weekly schedule mapping day name to working hours string (CLEANER only)",
            example = "{\"MONDAY\": \"08:00-18:00\", \"FRIDAY\": \"09:00-15:00\"}")
    private Map<String, String> schedule;

    @Schema(description = "List of areas the cleaner serves (CLEANER only)")
    private List<String> serviceArea;

    @Schema(description = "Whether the cleaner is currently available for new jobs (CLEANER only)")
    private Boolean available;

    @Schema(description = "Total number of jobs completed (CLEANER only)", example = "89")
    private Integer totalJobsCompleted;
}
