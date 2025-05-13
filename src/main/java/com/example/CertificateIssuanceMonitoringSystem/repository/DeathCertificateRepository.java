package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.DeathCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeathCertificateRepository extends JpaRepository<DeathCertificate,Long> {
    long countByStatus(String status);

    DeathCertificate getCertificateById(Long id);
}
