package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "birth_certificates")
public class BirthCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String name;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    // Personal Details
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "mother_name", nullable = false)
    private String motherName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "birth_place", nullable = false)
    private String birthPlace;

    @Column(name = "gender", nullable = false)
    private String gender;

    // Caste Certificate Details
    @Column(name = "caste", nullable = false)
    private String caste;

    @Column(name = "religion", nullable = false)
    private String religion;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "purpose", nullable = false)
    private String purpose;

    // Application Status
    @Column(name = "status", nullable = false)
    private String status = "Pending";

    @Column(name = "remarks")
    private String remarks;

    // File Paths for Uploaded Documents
    @Column(name = "aadhar_card_path")
    private String aadharCardPath;

    @Column(name = "birth_proof_path")
    private String birthProofPath;

    @Column(name = "caste_proof_path")
    private String casteProofPath;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name="signature",nullable = false)
    private String signature;



     private LocalDate rejectedDate;

    @Column(name="application_date")
    private LocalDate applicationDate;
}