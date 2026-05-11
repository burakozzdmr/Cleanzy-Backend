package com.burakozdemir.cleanzy.review.controller;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.review.dto.ReviewRequestDTO;
import com.burakozdemir.cleanzy.review.dto.ReviewResponseDTO;
import com.burakozdemir.cleanzy.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Review management")
@SecurityRequirement(name = "bearerAuth")
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    @Override
    @PostMapping
    @Operation(summary = "Create a review for a completed job")
    public ResponseEntity<ApiSuccessResponse<ReviewResponseDTO>> createReview(
            @Valid @RequestBody ReviewRequestDTO request
    ) {
        User authenticatedUser = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(authenticatedUser.getId(), request));
    }

    @Override
    @GetMapping("/{userId}")
    @Operation(summary = "Get all reviews for a user (as reviewee)")
    public ResponseEntity<ApiSuccessResponse<List<ReviewResponseDTO>>> getReviewsForUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(reviewService.getReviewsForUser(userId));
    }
}
