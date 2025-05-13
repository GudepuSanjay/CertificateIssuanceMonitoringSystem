package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.ResidenceCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidenceCertifcateRepository extends JpaRepository<ResidenceCertificate,Long> {
    long countByStatus(String status);

    ResidenceCertificate getCertificateById(Long id);
}
