package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.MarriageCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarriageCertificateRepository extends JpaRepository<MarriageCertificate,Long> {
    long countByStatus(String status);

    MarriageCertificate getCertificateById(Long id);
}
