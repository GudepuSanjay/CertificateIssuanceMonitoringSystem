package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.repository.DeathCertificateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.DeathCertificateService;
import com.example.CertificateIssuanceMonitoringSystem.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CertificateIssuanceMonitoringSystem.model.DeathCertificate;


import java.io.IOException;

@Controller
public class DeathCertificateController {
    @Autowired
    private DeathCertificateService deathCertificateService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private DeathCertificateRepository deathCertificateRepository;

    @GetMapping("/death-certificate-form")
    public String getDeathCertificateForm(@RequestParam(name = "value",required = false)String name, Model model)
    {
        model.addAttribute("name",name);
        model.addAttribute("deathcertificate",new DeathCertificate());
        return "death-certificate-form";
    }

    @GetMapping("/user-view-death-certificate/{id}")
    public String UserGetApplicationDetails(@PathVariable Long id, Model model)
    {
        DeathCertificate deathCert=deathCertificateService.getCertificateById(id);
        System.out.println(deathCert);
        if (deathCert == null) {
            // Handle not found case
            return "error/not-found";
        }
        model.addAttribute("details",deathCert);
        return "user-view-death-applications-details";
    }

    @GetMapping("/admin-view-applications/death/{id}")
    public String adminGetApplicationDetails(@PathVariable Long id,Model model)
    {
        DeathCertificate deathCert=deathCertificateService.getCertificateById(id);
        model.addAttribute("details",deathCert);
        return "admin-view-death-applications-details";
    }

    @PostMapping("/submit-death-certificate")
    public String submitDeathCertificate(
            @ModelAttribute DeathCertificate deathCertificate,
            @RequestParam("proofOfDeathFile") MultipartFile proofOfDeathFile,
            @RequestParam("deceasedIdProofFile") MultipartFile deceasedIdProofFile,
            @RequestParam("addressProofFile") MultipartFile addressProofFile,
            @RequestParam("informantIdProofFile") MultipartFile informantIdProofFile,
            @RequestParam(value = "affidavitFile", required = false) MultipartFile affidavitFile,
            RedirectAttributes redirectAttributes) {


        try {
            // Save files
            String proofOfDeathPath = deathCertificateService.saveFile(proofOfDeathFile);
            String deceasedIdProofPath = deathCertificateService.saveFile(deceasedIdProofFile);
            String addressProofPath = deathCertificateService.saveFile(addressProofFile);
            String informantIdProofPath = deathCertificateService.saveFile(informantIdProofFile);
            String affidavitPath = deathCertificateService.saveFile(affidavitFile);

            // Set file paths
            deathCertificate.setProofOfDeathPath(proofOfDeathPath);
            deathCertificate.setDeceasedIdProofPath(deceasedIdProofPath);
            deathCertificate.setAddressProofPath(addressProofPath);
            deathCertificate.setInformantIdProofPath(informantIdProofPath);
            deathCertificate.setAffidavitPath(affidavitPath);

            // Set default values
            deathCertificate.setRemarks("");

            // Save to database
            deathCertificateService.saveCertificate(deathCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error processing files: " + e.getMessage());
            return "redirect:/apply-certificate";
        }
    }

    @GetMapping("/download/death/{id}")
    public ResponseEntity<byte[]> downloadBirthCertificate(@PathVariable Long id) {
        try {
            DeathCertificate certificate = deathCertificateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateDeathCertificatePdf(
                    certificate.getDeceasedName(),
                    certificate.getId(),
                    String.valueOf(certificate.getDeathDate()),
                    certificate.getCauseOfDeath(),
                    certificate.getAgeAtDeath(),
                    certificate.getDeathPlaceAddress(),
                    certificate.getFatherOrHusbandName(),
                    certificate.getPermanentAddress()
            );

            String filename = "DeathCertificate_" + id + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating certificate: " + e.getMessage()).getBytes());
        }
    }

}
