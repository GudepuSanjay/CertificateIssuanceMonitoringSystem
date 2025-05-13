package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.DeathCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.DeathCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeathCertificateService {
    @Autowired
    private DeathCertificateRepository deathCertificateRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public DeathCertificate getCertificateById(Long id) {
        DeathCertificate application=deathCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not Found"));
        return application;
    }

    public void saveCertificate(DeathCertificate deathCertificate) {
        deathCertificateRepository.save(deathCertificate);
    }

    public String saveFile(MultipartFile file) throws IOException {
        if(file.isEmpty())
        {
            throw new IOException("File Not Found");
        }
        File uploadDir =new File(UPLOAD_DIR);

        if(!uploadDir.exists())
        {
            uploadDir.mkdirs();
        }
        String fileName= System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath= Paths.get(UPLOAD_DIR+fileName);
        Files.write(filePath,file.getBytes());
        return filePath.toString();
    }

    public List<DeathCertificate> getAllDeathCertificates() {
        return deathCertificateRepository.findAll() !=null ? deathCertificateRepository.findAll() : new ArrayList<>();
    }

    public DeathCertificate updateStatus(Long id, String status, String remarks) {
        DeathCertificate certificate=deathCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not Found"));
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return deathCertificateRepository.save(certificate);
    }

    public long countApplications() {
        return deathCertificateRepository.count();
    }

    public long countByStatus(String status) {
        return deathCertificateRepository.countByStatus(status);
    }
}
