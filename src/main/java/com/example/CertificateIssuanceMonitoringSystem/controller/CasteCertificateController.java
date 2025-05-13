package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.CasteCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.CasteCertificateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.CasteCertificateService;
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

import java.io.IOException;

@Controller
public class CasteCertificateController {
    @Autowired
    private CasteCertificateService casteCertificateService;

    @Autowired
    private CasteCertificateRepository casteCertificateRepository;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/caste-certificate-form")
    public String getCasterCertificateForm(@RequestParam(name = "value",required = false) String name, Model model){
        model.addAttribute("name",name);
        model.addAttribute("castecertificate",new CasteCertificate());
        return "caste-certificate-form";
    }

    @GetMapping("/user-view-caste-certificate/{id}")
    public String UserGetApplicationDetails(@PathVariable Long id, Model model)
    {
        CasteCertificate casteCert=casteCertificateService.getCertificateById(id);
        System.out.println(casteCert);
        if (casteCert == null) {
            // Handle not found case
            return "error/not-found";
        }
        model.addAttribute("details",casteCert);
        return "user-view-caste-applications-details";
    }

    @GetMapping("/admin-view-applications/caste/{id}")
    public String adminGetApplicationDetails(@PathVariable Long id,Model model)
    {
        CasteCertificate casteCert=casteCertificateService.getCertificateById(id);
        model.addAttribute("details",casteCert);
        return "admin-view-caste-applications-details";
    }

    @PostMapping("/submit-caste-certificate")
    public String submitCasteCertificate(@ModelAttribute CasteCertificate casteCertificate,
                                             @RequestParam("aadharCardFile") MultipartFile aadharCardFile,
                                             @RequestParam("addressProofFile") MultipartFile addressProofFile,
                                             @RequestParam("casteProofFile") MultipartFile casteProofFile,
                                             @RequestParam("photoFile") MultipartFile photoFile,
                                             @RequestParam("signatureFile") MultipartFile signatureFile,
                                             RedirectAttributes redirectAttributes) {
        try {
//             Save the uploaded files
            String aadharCardPath = casteCertificateService.saveFile(aadharCardFile);
            String addressProofPath = casteCertificateService.saveFile(addressProofFile);
            String casteProofPath=casteCertificateService.saveFile(casteProofFile);
            String photoPath=casteCertificateService.saveFile(photoFile);
            String signaturePath=casteCertificateService.saveFile(signatureFile);


            // Set file paths in BirthCertificate object
            casteCertificate.setAadharCardPath(aadharCardPath);
            casteCertificate.setCasteProofPath(casteProofPath);
            casteCertificate.setAddressProofPath(addressProofPath);
            casteCertificate.setPhotoPath(photoPath);
            casteCertificate.setSignaturePath(signaturePath);

            casteCertificate.setRemarks("");
            // Save to database
            casteCertificateService.saveCertificate(casteCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading files.");
            return "redirect:/apply-certificate";
        }
    }

    @GetMapping("/download/caste/{id}")
    public ResponseEntity<byte[]> downloadCasteCertificate(@PathVariable Long id) {
        try {
            CasteCertificate certificate = casteCertificateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateCasteCertificatePdf(
                    certificate.getFullName(),
                    certificate.getFatherName(),
                    String.valueOf(certificate.getDateOfBirth()),
                    certificate.getMotherName()
            );

            String filename = "CasteCertificate_" + id + ".pdf";

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
