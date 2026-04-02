package com.burakozdemir.cleanzy.customer.entity;

import com.burakozdemir.cleanzy.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private User user;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "LOCATION")
    private String currentLocation;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "TOTAL_REVIEWS")
    private Integer totalReviews;

    @ElementCollection
    @CollectionTable(name = "CUSTOMER_SAVED_ADDRESSES", joinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    @MapKeyColumn(name = "ADDRESS_TITLE")
    @Column(name = "ADDRESS")
    private Map<String, String> savedAddresses;

    @Column(name = "PROFILE_PHOTO_URL")
    private String profilePhotoURL;

    @Column(name = "VERIFICATION_STATUS")
    private boolean isVerified;
}
