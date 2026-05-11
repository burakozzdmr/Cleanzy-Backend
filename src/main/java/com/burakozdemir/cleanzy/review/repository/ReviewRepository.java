package com.burakozdemir.cleanzy.review.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.job.entity.Job;
import com.burakozdemir.cleanzy.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByJobAndReviewer(Job job, User reviewer);

    List<Review> findAllByReviewee(User reviewee);

    List<Review> findAllByRevieweeOrderByCreatedAtDesc(User reviewee);
}
