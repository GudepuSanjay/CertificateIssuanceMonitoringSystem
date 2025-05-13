package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.CasteCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.CasteCertificateRepository;
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
public class CasteCertificateService {

    public static final String UPLOAD_DIR="uploads/";

    @Autowired
    private CasteCertificateRepository casteCertificateRepository;

    public List<CasteCertificate> getAllCasteCertificates() {
        return casteCertificateRepository.findAll() !=null ? casteCertificateRepository.findAll() : new ArrayList<>();
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


    public void saveCertificate(CasteCertificate casteCertificate) {
        casteCertificateRepository.save(casteCertificate);
    }

    public CasteCertificate getCertificateById(Long id) {
        CasteCertificate application=casteCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not Found"));
        return application;
    }

    public CasteCertificate updateStatus(Long id, String status, String remarks) {
        CasteCertificate certificate =casteCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not Found"));
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return casteCertificateRepository.save(certificate);
    }

    public long countApplications() {
        return casteCertificateRepository.count();
    }

    public long countByStatus(String status) {
        return casteCertificateRepository.countByStatus(status);
    }
}

