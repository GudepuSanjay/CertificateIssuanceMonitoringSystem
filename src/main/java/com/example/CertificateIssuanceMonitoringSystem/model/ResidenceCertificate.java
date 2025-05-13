package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "residence_certificate_applications")
public class ResidenceCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Personal Details
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String nationality;

    // Address Details
    @Column(nullable = false, length = 500)
    private String currentAddress;

    @Column(nullable = false,length = 500)
    private String permanentAddress;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private Integer residenceYears; // Duration in years

    // Documents
    @Column(nullable = false)
    private String aadhaarPath;

    @Column(nullable = false)
    private String addressProofPath;

    @Column(nullable = false)
    private String photoPath;

    @Column(nullable = false)
    private String affidavitPath;

//    @Column(nullable = false)
//    private String ageProofPath;

    // Status
    @Column(nullable = false)
    private String status = "Pending";

    @Column(name = "application_date",nullable = false)
    private LocalDate applicationDate = LocalDate.now();

    @Column(name = "remarks")
    private String remarks;
}