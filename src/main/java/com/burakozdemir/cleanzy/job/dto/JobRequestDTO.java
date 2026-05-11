package com.burakozdemir.cleanzy.job.dto;

import com.burakozdemir.cleanzy.common.util.HouseSizeType;
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
    private Long cleanerId;

    @NotNull
    private Long customerId;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotNull
    private LocalDateTime scheduledAt;

    @NotBlank
    private String timeSlot;

    @NotNull
    private HouseSizeType houseSize;

    private List<String> extraServices;

    @NotNull
    private Double totalPrice;

    private String notes;
}
