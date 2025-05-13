package com.example.CertificateIssuanceMonitoringSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "IncomeCertificateDetails")
public class IncomeCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName",nullable = false)
    private String fullName;

    @Column(name = "fatherName",nullable = false)
    private String fatherName;

    @Column(name = "motherName",nullable = false)
    private String motherName;

    @Column(name = "dateOfBirth",nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender",nullable = false)
    private String gender;

    @Column(name = "Religion")
    private String religion;

    @Column(name = "AnnualIncome",nullable = false)
    private Double annualIncome;

    @Column(name = "occupation",nullable = false)
    private String occupation;

    @Column(name = "purpose",nullable = false)
    private String purpose;

    @Column(name = "status",nullable = false)
    private String status="Pending";

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "district",nullable = false)
    private String district;

    @Column(name = "pinCode",nullable = false)
    private Integer pinCode;

    @Column(name = "aadharCardPath",nullable = false)
    private String aadharCardPath;

    @Column(name = "addressProofPath",nullable = false)
    private String addressProofPath;

    @Column(name="incomeProofPath",nullable = false)
    private String incomeProofPath;

    @Column(name ="photo",nullable = false)
    private String photoPath;

    @Column(name="signature",nullable = false)
    private String signaturePath;

    @Column(name = "incomeSource", nullable = false)
    private String incomeSource;

    @Column(name = "familyMembers", nullable = false)
    private Integer familyMembers;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "familyAnnualIncome", nullable = false)
    private Double familyAnnualIncome;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "applicationDate", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "mobileNumber", nullable = false)
    private String mobileNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "aadharNumber", nullable = false)
    private String aadharNumber;



}
