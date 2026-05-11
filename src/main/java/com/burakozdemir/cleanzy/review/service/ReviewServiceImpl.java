package com.burakozdemir.cleanzy.review.service;

import com.burakozdemir.cleanzy.auth.entity.Role;
import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.job.entity.Job;
import com.burakozdemir.cleanzy.job.repository.JobRepository;
import com.burakozdemir.cleanzy.review.dto.ReviewRequestDTO;
import com.burakozdemir.cleanzy.review.dto.ReviewResponseDTO;
import com.burakozdemir.cleanzy.review.entity.Review;
import com.burakozdemir.cleanzy.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final JobRepository jobRepository;
    private final CleanerRepository cleanerRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public ApiSuccessResponse<ReviewResponseDTO> createReview(Long reviewerUserId, ReviewRequestDTO request) {
        User reviewer = authRepository.findById(reviewerUserId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        User reviewee = authRepository.findById(request.getRevieweeId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BusinessException(ErrorType.JOB_NOT_FOUND));

        if (job.getStatus() != JobStatusType.COMPLETED) {
            throw new BusinessException(ErrorType.JOB_NOT_COMPLETED);
        }

        if (reviewRepository.findByJobAndReviewer(job, reviewer).isPresent()) {
            throw new BusinessException(ErrorType.REVIEW_ALREADY_EXISTS);
        }

        Review review = new Review();
        review.setJob(job);
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review saved = reviewRepository.save(review);

        recalculateRating(reviewee);

        return ApiSuccessResponse.of(toResponseDTO(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<ReviewResponseDTO>> getReviewsForUser(Long userId) {
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        List<ReviewResponseDTO> reviews = reviewRepository
                .findAllByRevieweeOrderByCreatedAtDesc(user)
                .stream()
                .map(this::toResponseDTO)
                .toList();

        return ApiSuccessResponse.of(reviews, reviews.size());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void recalculateRating(User reviewee) {
        List<Review> allReviews = reviewRepository.findAllByReviewee(reviewee);
        OptionalDouble avg = allReviews.stream()
                .mapToInt(Review::getRating)
                .average();

        if (reviewee.getRole() == Role.CLEANER) {
            cleanerRepository.findByUser(reviewee).ifPresent(cleaner -> {
                cleaner.setRating(avg.orElse(0.0));
                cleaner.setTotalReviews(allReviews.size());
                cleanerRepository.save(cleaner);
            });
        } else if (reviewee.getRole() == Role.CUSTOMER) {
            customerRepository.findByUser(reviewee).ifPresent(customer -> {
                customer.setRating(avg.orElse(0.0));
                customer.setTotalReviews(allReviews.size());
                customerRepository.save(customer);
            });
        }
    }

    private String resolvePhotoURL(User user) {
        return switch (user.getRole()) {
            case CLEANER -> cleanerRepository.findByUser(user)
                    .map(Cleaner::getProfilePhotoURL)
                    .orElse(null);
            case CUSTOMER -> customerRepository.findByUser(user)
                    .map(Customer::getProfilePhotoURL)
                    .orElse(null);
        };
    }

    private ReviewResponseDTO toResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .jobId(review.getJob().getId())
                .reviewerName(review.getReviewer().getFullName())
                .reviewerPhotoURL(resolvePhotoURL(review.getReviewer()))
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
