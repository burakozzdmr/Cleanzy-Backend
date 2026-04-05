package com.burakozdemir.cleanzy.job.entity;

import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "JOBS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    private String title;
    private String description;

    private String address;
    private String city;

    private Double price;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private JobStatusType status;

    @ManyToOne
    @JoinColumn(name = "CLEANER_ID")
    private Cleaner assignedCleaner;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}