package com.burakozdemir.cleanzy.review.entity;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.job.entity.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "REVIEWS",
        uniqueConstraints = @UniqueConstraint(columnNames = {"JOB_ID", "REVIEWER_ID"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "JOB_ID", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "REVIEWER_ID", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "REVIEWEE_ID", nullable = false)
    private User reviewee;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
