package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.CasteCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasteCertificateRepository extends JpaRepository<CasteCertificate,Long> {
    long countByStatus(String status);

    CasteCertificate getCertificateById(Long id);
}
