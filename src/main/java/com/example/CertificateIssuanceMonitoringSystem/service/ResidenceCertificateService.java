package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.ResidenceCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.ResidenceCertifcateRepository;
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
public class ResidenceCertificateService {
    @Autowired
    private ResidenceCertifcateRepository residenceCertifcateRepository;

    public static final String UPLOAD_DIR="uploads/";

    public ResidenceCertificate getCertificateById(Long id) {
        ResidenceCertificate residenceCert=residenceCertifcateRepository.findById(id).orElseThrow(()->new RuntimeException("Application Not found"));
        return residenceCert;
    }

    public List<ResidenceCertificate> getALlResidenceCertificates() {
        return residenceCertifcateRepository.findAll() !=null ? residenceCertifcateRepository.findAll() :new ArrayList<>();
    }

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

    public ResidenceCertificate saveCertificate(ResidenceCertificate residenceCertificate) {
        return residenceCertifcateRepository.save(residenceCertificate);
    }



    public ResidenceCertificate updateStatus(Long id, String status, String remarks) {
        if (!residenceCertifcateRepository.existsById(id)) {
            throw new RuntimeException("Residence Certificate application not found with ID: " + id);
        }

        ResidenceCertificate certificate = residenceCertifcateRepository.getById(id);
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return residenceCertifcateRepository.save(certificate);
    }

    public long countApplications() {
        return residenceCertifcateRepository.count();
    }

    public long countByStatus(String status) {
        return residenceCertifcateRepository.countByStatus(status);
    }
}
