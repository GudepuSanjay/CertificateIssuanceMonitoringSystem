package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Cast_Certificate_Details")
public class CasteCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(name = "fatherName", nullable = false)
    private String fatherName;

    @Column(name = "motherName", nullable = false)
    private String motherName;

    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "religion", nullable = false)
    private String religion;

    @Column(name = "casteCategory", nullable = false)
    private String casteCategory; // SC/ST/OBC/General

    @Column(name = "subCaste")
    private String subCaste;

    @Column(name = "fatherCasteCertificateNo")
    private String fatherCasteCertificateNo;

    @Column(name = "grandfatherName")
    private String grandfatherName;

    @Column(name = "permanentAddress", nullable = false, length = 500)
    private String permanentAddress;

    @Column(name = "currentAddress", length = 500)
    private String currentAddress;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "purpose", nullable = false)
    private String purpose; // Education/Employment/Govt Scheme/Other

    @Column(name = "status", nullable = false)
    private String status = "Pending";

    @Column(name = "remarks")
    private String remarks;

    @Column(name="application_Date")
    private LocalDate applicationDate;

    @Column(name = "aadharCardPath", nullable = false)
    private String aadharCardPath;

    @Column(name = "addressProofPath", nullable = false)
    private String addressProofPath;

    @Column(name = "casteProofPath", nullable = false)
    private String casteProofPath; // Father's certificate or School LC

    @Column(name = "photoPath", nullable = false)
    private String photoPath;

    @Column(name = "signaturePath", nullable = false)
    private String signaturePath;
}