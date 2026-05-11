package com.burakozdemir.cleanzy.review.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.review.dto.ReviewRequestDTO;
import com.burakozdemir.cleanzy.review.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ApiSuccessResponse<ReviewResponseDTO> createReview(Long reviewerUserId, ReviewRequestDTO request);

    ApiSuccessResponse<List<ReviewResponseDTO>> getReviewsForUser(Long userId);
}
