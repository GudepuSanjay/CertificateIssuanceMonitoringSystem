package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.MarriageCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.MarriageCertificateRepository;
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
public class MarriageCertificateService {
    @Autowired
    private MarriageCertificateRepository marriageCertificateRepository;

    public static final String UPLOAD_DIR="uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if(file.isEmpty())
        {
            throw new IOException("File Not Found");
        }
        File uploadDir=new File(UPLOAD_DIR);
        if(!uploadDir.exists())
        {
            uploadDir.mkdirs();
        }
        String fileName= System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath= Paths.get(UPLOAD_DIR+fileName);
        Files.write(filePath,file.getBytes());
        return filePath.toString();
    }

    public MarriageCertificate saveCertificate(MarriageCertificate marriageCertificate)
    {
        marriageCertificateRepository.save(marriageCertificate);
        return marriageCertificate;
    }

    public MarriageCertificate getCertificateById(Long id) {
        MarriageCertificate marriageCert=marriageCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Unable to get Application"));
        return marriageCert;
    }

    public List<MarriageCertificate> getAllMarriageCertificates() {
        return marriageCertificateRepository.findAll() !=null ? marriageCertificateRepository.findAll() :new ArrayList<>();
    }

    public MarriageCertificate updateStatus(Long id, String status, String remarks) {
        MarriageCertificate certificate =marriageCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not Found"));
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return marriageCertificateRepository.save(certificate);
    }

    public long countApplications() {
        return marriageCertificateRepository.count();
    }

    public long countByStatus(String status) {
        return marriageCertificateRepository.countByStatus(status);
    }
}
