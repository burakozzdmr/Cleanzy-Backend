package com.burakozdemir.cleanzy.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryDTO {
    private Long id;
    private String fullName;
    private String currentLocation;
    private Double rating;
}
