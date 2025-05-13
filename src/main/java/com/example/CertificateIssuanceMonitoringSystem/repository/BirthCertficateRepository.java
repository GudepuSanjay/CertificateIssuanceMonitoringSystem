package com.example.CertificateIssuanceMonitoringSystem.repository;

import com.example.CertificateIssuanceMonitoringSystem.model.BirthCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirthCertficateRepository extends JpaRepository<BirthCertificate,Long> {
//
//    List<BirthCertificate> findByUserName(String username) ;


//    List<BirthCertificate> findByUserName(String username);



    List<BirthCertificate> findByFullName(String fullName);

    List<BirthCertificate> findByStatus(String status);

    long countByStatus(String status);
    BirthCertificate getCertificateById(Long id);


}
