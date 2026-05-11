package com.burakozdemir.cleanzy.review.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.review.dto.ReviewRequestDTO;
import com.burakozdemir.cleanzy.review.dto.ReviewResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewController {

    ResponseEntity<ApiSuccessResponse<ReviewResponseDTO>> createReview(ReviewRequestDTO request);

    ResponseEntity<ApiSuccessResponse<List<ReviewResponseDTO>>> getReviewsForUser(Long userId);
}
