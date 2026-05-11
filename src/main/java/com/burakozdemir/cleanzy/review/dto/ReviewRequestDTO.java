package com.burakozdemir.cleanzy.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    @NotNull
    private Long jobId;

    @NotNull
    private Long revieweeId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(min = 10, max = 500)
    private String comment;
}
