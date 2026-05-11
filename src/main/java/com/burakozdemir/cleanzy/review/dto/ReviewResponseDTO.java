package com.burakozdemir.cleanzy.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private Long jobId;
    private String reviewerName;
    private String reviewerPhotoURL;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
