package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "marriage_certificate_applications")
public class MarriageCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bride Details
    @Column(nullable = false)
    private String brideFullName;

    @Column(nullable = false)
    private String brideFatherName;

    @Column(nullable = false)
    private LocalDate brideDob;

    @Column(nullable = false)
    private String brideReligion;

    @Column(nullable = false)
    private String brideOccupation;

    @Column(nullable = false)
    private String brideAadhaarNo;

    @Column(nullable = false, length = 500)
    private String brideAddress;

    // Groom Details
    @Column(nullable = false)
    private String groomFullName;

    @Column(nullable = false)
    private String groomFatherName;

    @Column(nullable = false)
    private LocalDate groomDob;

    @Column(nullable = false)
    private String groomReligion;

    @Column(nullable = false)
    private String groomOccupation;

    @Column(nullable = false)
    private String groomAadhaarNo;

    @Column(nullable = false, length = 500)
    private String groomAddress;

    // Marriage Details
    @Column(nullable = false)
    private LocalDate marriageDate;

    @Column(nullable = false)
    private String marriagePlace;

    @Column(nullable = false)
    private String marriageType; // Hindu/Muslim/Christian/Court

    @Column(nullable = false)
    private String previousMaritalStatus; // Single/Divorced/Widowed

    // Witness Details
    @Column(nullable = false)
    private String witness1Name;

    @Column(nullable = false)
    private String witness1Aadhaar;

    @Column(nullable = false)
    private String witness2Name;

    @Column(nullable = false)
    private String witness2Aadhaar;

    // Registration Details
    @Column(unique = true)
    private String registrationNumber;

    private String district;
    private String state;

    // Document Paths
    @Column(nullable = false)
    private String brideAadhaarPath;

    @Column(nullable = false)
    private String brideBirthProofPath;

    @Column(nullable = false)
    private String groomAadhaarPath;

    @Column(nullable = false)
    private String groomBirthProofPath;

    @Column(nullable = false)
    private String marriageAffidavitPath;

    private String weddingPhotosPath;

    // Application Status
    @Column(nullable = false)
    private String status = "Pending";

    private String remarks;

    @Column(nullable = false)
    private LocalDate applicationDate = LocalDate.now();
}