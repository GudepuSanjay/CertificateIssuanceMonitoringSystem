package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.ResidenceCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.ResidenceCertifcateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.PdfGeneratorService;
import com.example.CertificateIssuanceMonitoringSystem.service.ResidenceCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ResidenceCertificateController {
    @Autowired
    private ResidenceCertificateService residenceCertificateService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private ResidenceCertifcateRepository residenceCertifcateRepository;

    @GetMapping("/residence-certificate-form")
    public String getResidenceCetificateForm(@RequestParam(name = "value",required = false) String name, Model model)
    {
        model.addAttribute("name",name);
        model.addAttribute("residencecertificate",new ResidenceCertificate());
        return "residence-certificate-form";
    }

    @PostMapping("/submit-residence-certificate")
    public String submitresidenceCertificate(
            @ModelAttribute ResidenceCertificate residenceCertificate,
            @RequestParam("aadharFile") MultipartFile aadharFile,
            @RequestParam("addressFile") MultipartFile addressFile,
            @RequestParam("photoFile") MultipartFile photoFile,
            @RequestParam("affidavitFile") MultipartFile affidavitFile,
//            @RequestParam("ageProofPath") MultipartFile ageProofFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Save files and set paths
                String aadhaarPath=residenceCertificateService.saveFile(aadharFile);
                String  addressProofPath=residenceCertificateService.saveFile(addressFile);
                String photoPath=residenceCertificateService.saveFile(photoFile);
                String affidavitPath=residenceCertificateService.saveFile(affidavitFile);
//                String ageProofPath=residenceCertificateService.saveFile(ageProofFile);

                residenceCertificate.setAadhaarPath(aadhaarPath);
                residenceCertificate.setAddressProofPath(addressProofPath);
                residenceCertificate.setPhotoPath(photoPath);
                residenceCertificate.setAffidavitPath(affidavitPath);
//                residenceCertificate.setAgeProofPath(ageProofPath);
                residenceCertificate.setRemarks("");
            // Save the application
            residenceCertificateService.saveCertificate(residenceCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading files. Please try again.");
            return "redirect:/apply-certificate";
        }
    }

    @GetMapping("/user-view-residence-certificate/{id}")
    public String UserGetApplicationDetails(@PathVariable Long id, Model model)
    {
        ResidenceCertificate residenceCert=residenceCertificateService.getCertificateById(id);
        System.out.println(residenceCert);
        if(residenceCert==null)
        {
            return "error/not-found";
        }
        model.addAttribute("details",residenceCert);
        return "user-view-residence-applications-details";
    }
    @GetMapping("/admin-view-applications/residence/{id}")
    public String adminGetApplicationDetails(@PathVariable Long id,Model model)
    {
        ResidenceCertificate residenceCert=residenceCertificateService.getCertificateById(id);
        model.addAttribute("details",residenceCert);
        return "admin-view-residence-applications-details";
    }


    @GetMapping("/download/residence/{id}")
    public ResponseEntity<byte[]> downloadBirthCertificate(@PathVariable Long id) {
        try {
            ResidenceCertificate certificate = residenceCertifcateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateResidenceCertificatePdf(
                    certificate.getFullName(),
                    certificate.getId(),
                    certificate.getFatherName(),
                    certificate.getGender(),
                    certificate.getDistrict(),
                    certificate.getState(),
                    String.valueOf(certificate.getDateOfBirth()),
                    certificate.getNationality()
            );

            String filename = "ResidenceCertificate_" + id + ".pdf";

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
