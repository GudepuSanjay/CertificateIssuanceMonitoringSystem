package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.IncomeCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.IncomeCertificateRepository;
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
public class IncomeCertificateService {
    @Autowired
    private IncomeCertificateRepository incomeCertificateRepository;

    private static final String UPLOAD_DIR = "uploads/";

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
        Path filePath=Paths.get(UPLOAD_DIR+fileName);
        Files.write(filePath,file.getBytes());
        return filePath.toString();
    }

    public void saveCertificate(IncomeCertificate incomeCertificate) {
        incomeCertificateRepository.save(incomeCertificate);
    }

    public List<IncomeCertificate> getAllIncomeCertificates() {
        return incomeCertificateRepository.findAll() != null ? incomeCertificateRepository.findAll() : new ArrayList<>();
    }

    public List<IncomeCertificate> getCertificateByUsername(String username) {
        List<IncomeCertificate> applications=incomeCertificateRepository.findByFullName(username);
        return applications;
    }

    public IncomeCertificate getCertificateById(Long id) {
        IncomeCertificate application = incomeCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Certificate Not Found"));
        return application;
    }

    public IncomeCertificate updateStatus(Long id, String status, String remarks) {
        IncomeCertificate certificate=incomeCertificateRepository.findById(id).orElseThrow(()->new RuntimeException("Certificate Not Found"));
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return incomeCertificateRepository.save(certificate);
    }

    public long countApplications() {
        return incomeCertificateRepository.count();
    }

    public long countByStatus(String status) {
        return incomeCertificateRepository.countByStatus(status);
    }
}
