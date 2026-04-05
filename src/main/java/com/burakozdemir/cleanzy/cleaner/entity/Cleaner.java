package com.burakozdemir.cleanzy.cleaner.entity;


import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "CLEANERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cleaner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private User user;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "LOCATION")
    private String currentLocation;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "TOTAL_REVIEWS")
    private Integer totalReviews;

    @Column(name = "IBAN_NUMBER")
    private String ibanNumber;

    @Column(name = "HOURLY_RATE")
    private BigDecimal hourlyRate;

    @ElementCollection
    @CollectionTable(name = "CLEANER_SERVICE_AREA", joinColumns = @JoinColumn(name = "CLEANER_ID"))
    @Column(name = "SERVICE_AREA")
    private Set<String> serviceArea;

    @Column(name = "PROFILE_PHOTO_URL")
    private String profilePhotoURL;

    @ElementCollection
    @CollectionTable(name = "CLEANER_SCHEDULE", joinColumns = @JoinColumn(name = "CLEANER_ID"))
    @MapKeyColumn(name = "DAY")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "WORKING_HOURS")
    private Map<DayOfWeek, String> schedule;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "CLEANER_SERVICES", joinColumns = @JoinColumn(name = "CLEANER_ID"))
    @Column(name = "SERVICES")
    private Set<ServiceType> services;

    @Column(name = "VERIFICATION_STATUS")
    private boolean isVerified;

    @Column(name = "AVAILABLE_STATUS")
    private boolean isAvailable;
}
