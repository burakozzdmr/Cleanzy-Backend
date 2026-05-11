package com.burakozdemir.cleanzy.job.dto;

import com.burakozdemir.cleanzy.common.util.HouseSizeType;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class JobRequestDTO {

    @NotNull
    @JsonAlias("cleanerID")
    private Long cleanerId;

    @NotNull
    @JsonAlias("customerID")
    private Long customerId;

    @NotBlank
    private String address;

    // Optional — iOS does not send this field; service defaults to empty string when absent
    private String city;

    // ── Scheduling ────────────────────────────────────────────────────────────
    // iOS sends scheduledDate ("yyyy-MM-dd") and scheduledTime ("HH:mm") as
    // separate strings. The service combines them into scheduledAt.
    // Clients that already build the full LocalDateTime can send scheduledAt directly.

    private String scheduledDate;   // "yyyy-MM-dd"

    private String scheduledTime;   // "HH:mm"   – also used as timeSlot display string

    private LocalDateTime scheduledAt;  // set directly by non-iOS clients

    private String timeSlot;        // display string, e.g. "09:00 - 13:00"

    // ── Job details ───────────────────────────────────────────────────────────

    @NotNull
    private HouseSizeType houseSize;

    private List<String> extraServices;

    // Optional — iOS does not calculate price on client; service can default to 0
    private Double totalPrice;

    private String notes;
}
