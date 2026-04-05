package com.burakozdemir.cleanzy.customer.dto;

import com.burakozdemir.cleanzy.auth.dto.UserSummaryDTO;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO implements ProfileDTO {
    private Long id;
    private UserSummaryDTO user;
    private String currentLocation;
    private Double rating;
    private Integer totalReviews;
    private String profilePhotoURL;
    private boolean isVerified;
}
