package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.IncomeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeCertificateRepository extends JpaRepository<IncomeCertificate,Long> {
List<IncomeCertificate> findByFullName(String username);
long countByStatus(String status);

    IncomeCertificate getCertificateById(Long id);
}
