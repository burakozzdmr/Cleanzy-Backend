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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "BIOGRAPHY")
    private String biography;

    @Column(name = "CURRENT_LOCATION")
    private String currentLocation;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "IBAN_NUMBER")
    private String ibanNumber;

    @Column(name = "HOURLY_RATE")
    private BigDecimal hourlyRate;

    @ElementCollection
    @CollectionTable(name = "cleaner_service_area", joinColumns = @JoinColumn(name = "cleaner_id"))
    @Column(name = "SERVICE_AREA")
    private Set<String> serviceArea;

    @Column(name = "PROFILE_PHOTO_URL")
    private String profilePhotoURL;

    @ElementCollection
    @CollectionTable(name = "cleaner_schedule", joinColumns = @JoinColumn(name = "cleaner_id"))
    @MapKeyColumn(name = "day")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "WORKING_HOURS")
    private Map<DayOfWeek, String> schedule;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "cleaner_services", joinColumns = @JoinColumn(name = "cleaner_id"))
    @Column(name = "SERVICES")
    private Set<ServiceType> services;

    @Column(name = "VERIFICATION_STATUS")
    private boolean isVerification;

    @Column(name = "AVAILABLE_STATUS")
    private boolean isAvailable;
}
