package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.BirthCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class BirthCertificateService {
    @Autowired
    private BirthCertficateRepository birthCertficateRepository;
    @Autowired
    private IncomeCertificateRepository incomeCertificateRepository;
    @Autowired
    private CasteCertificateRepository casteCertificateRepository;
    @Autowired
    private ResidenceCertifcateRepository residenceCertifcateRepository;
    @Autowired
    private MarriageCertificateRepository marriageCertificateRepository;
    @Autowired
    private DeathCertificateRepository deathCertificateRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        // Ensure directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Save file
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());

        return filePath.toString(); // Return the file path
    }

    public void saveCertificate(BirthCertificate certificate) {
        birthCertficateRepository.save(certificate);
    }


    public BirthCertificate getCertificateById(Long id) {
        BirthCertificate birthApplication= birthCertficateRepository.findById(id).orElseThrow(()->new RuntimeException("Certificate Not Found"));
        return birthApplication;
    }


    public void saveBirthCertificate(BirthCertificate birthCertificate) {
        birthCertificate.setStatus("Pending");
        birthCertficateRepository.save(birthCertificate);
    }
    public List<BirthCertificate> getAllBirthCertificates() {
//        List<BirthCertificate> applications = birthCertficateRepository.findAll();
//        return applications != null ? applications : new ArrayList<>();
        return birthCertficateRepository.findAll() != null ? birthCertficateRepository.findAll() : new ArrayList<>();
    }


    public BirthCertificate updateStatus(Long id,String status,String remarks)
    {
        BirthCertificate certificate=birthCertficateRepository.findById(id).orElseThrow(()->new RuntimeException("BirthCertificate Not Found"));
        certificate.setStatus(status);
        certificate.setRemarks(remarks);
        return birthCertficateRepository.save(certificate);
    }

    public List<BirthCertificate> getApplicationsByStatus(String status) {
        List<BirthCertificate> applications=birthCertficateRepository.findByStatus(status);
        return applications;
    }

    public long countApplications() {
        return birthCertficateRepository.count();
    }

    public List<BirthCertificate> getCertificateByUsername(String username) {
        List<BirthCertificate> applications=birthCertficateRepository.findByFullName(username);
        return applications;
    }

    public long totalByStatus(String status) {
         return birthCertficateRepository.countByStatus(status)+ incomeCertificateRepository.countByStatus(status)+casteCertificateRepository.countByStatus(status)+residenceCertifcateRepository.countByStatus(status)+marriageCertificateRepository.countByStatus(status)+birthCertficateRepository.countByStatus(status)+deathCertificateRepository.countByStatus(status);

    }

    public long countByStatus(String status) {
        return birthCertficateRepository.countByStatus(status);
    }

    public long totalCertificatesApplications() {
        return birthCertficateRepository.count()+ incomeCertificateRepository.count()+casteCertificateRepository.count()+residenceCertifcateRepository.count()+marriageCertificateRepository.count()+deathCertificateRepository.count();
    }

//    public List<BirthCertificate> getBirthCertificateByUsername(String username) {
//        return BirthCertficateRepository.findByUserName(username);
//    }

    public List<BirthCertificate> getAllBirthCertificatesByUsername(String username) {
        return birthCertficateRepository.findByFullName(username);
    }
//
//    public String typeOfCertificate() {
//
//    }
}
