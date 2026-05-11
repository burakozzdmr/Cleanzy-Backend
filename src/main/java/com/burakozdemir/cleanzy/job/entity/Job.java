package com.burakozdemir.cleanzy.job.entity;

import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.common.util.HouseSizeType;
import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "CLEANER_ID")
    private Cleaner assignedCleaner;

    private String title;
    private String description;

    @Column(nullable = false)
    private String address;

    // Not sent by the iOS client; stored as empty string when absent.
    private String city;

    @Column(name = "TIME_SLOT")
    private String timeSlot;

    // columnDefinition prevents Hibernate 6 from generating a CHECK constraint
    // with the old enum value names (ONE_PLUS_ONE etc.) that would reject new inserts.
    @Enumerated(EnumType.STRING)
    @Column(name = "HOUSE_SIZE", columnDefinition = "varchar(50)")
    private HouseSizeType houseSize;

    @ElementCollection
    @CollectionTable(name = "JOB_EXTRA_SERVICES", joinColumns = @JoinColumn(name = "JOB_ID"))
    @Column(name = "SERVICE_KEY")
    private List<String> extraServices;

    @Column(name = "NOTES", length = 1000)
    private String notes;

    @Column(name = "TOTAL_PRICE")
    private Double price;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(30)")
    private JobStatusType status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
