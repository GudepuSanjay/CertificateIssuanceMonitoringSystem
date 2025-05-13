package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "death_certificate_applications")
public class DeathCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Deceased Details
    @Column(nullable = false)
    private String deceasedName;

    @Column(nullable = false)
    private String fatherOrHusbandName;

    @Column(nullable = false)
    private LocalDate deathDate;

//    private LocalTime deathTime;

    @Column(nullable = false)
    private Integer ageAtDeath;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, length = 500)
    private String causeOfDeath;

    @Column(nullable = false)
    private String deathType; // Natural/Accidental/etc

    @Column(nullable = false)
    private String proofOfDeathType; // e.g., "HospitalReport", "MCCD", etc.

    // Address Details
    @Column(nullable = false, length = 500)
    private String permanentAddress;

    @Column(nullable = false, length = 500)
    private String deathPlaceAddress;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String pincode;

    // Informant Details
    @Column(nullable = false)
    private String informantName;

    @Column(nullable = false)
    private String relationship;

    @Column(nullable = false, length = 500)
    private String informantAddress;

    @Column(nullable = false)
    private String contactNumber;

    private String informantAadhaar;

    // Death Particulars
    private String hospitalName;
    private String doctorName;

    // Document Paths
    @Column(nullable = false)
    private String proofOfDeathPath;

    @Column(nullable = false)
    private String deceasedIdProofPath;

    @Column(nullable = false)
    private String addressProofPath;

    @Column(nullable = false)
    private String informantIdProofPath;

    private String affidavitPath;

    // Status
    @Column(nullable = false)
    private String status = "Pending";

    @Column(name = "remarks")
    private String remarks;

    @Column(name="application_date",nullable = false)
    private LocalDate applicationDate;

}