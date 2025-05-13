package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.MarriageCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.MarriageCertificateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.MarriageCertificateService;
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
public class MarriageCertificateController {
    @Autowired
    private MarriageCertificateService marriageCertificateService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private MarriageCertificateRepository marriageCertificateRepository;

    @GetMapping("/marriage-certificate-form")
    public String showMarriageCertificateForm(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("marriageCertificate",new MarriageCertificate());
        return "marriage-certificate-form";
    }

    @PostMapping("/submit-marriage-certificate")
    public String submitMarriageCertificate(
            @ModelAttribute MarriageCertificate marriageCertificate,
            @RequestParam("brideAadhaarFile") MultipartFile brideAadhaarFile,
            @RequestParam("groomAadhaarFile") MultipartFile groomAadhaarFile,
            @RequestParam("brideBirthProofFile") MultipartFile brideBirthProofFile,
            @RequestParam("groomBirthProofFile") MultipartFile groomBirthProofFile,
            @RequestParam("marriageAffidavitFile") MultipartFile marriageAffidavitFile,
            @RequestParam(value = "weddingPhotosFile", required = false) MultipartFile weddingPhotosFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Save files and set paths
            marriageCertificate.setBrideAadhaarPath(marriageCertificateService.saveFile(brideAadhaarFile));
            marriageCertificate.setGroomAadhaarPath(marriageCertificateService.saveFile(groomAadhaarFile));
            marriageCertificate.setBrideBirthProofPath(marriageCertificateService.saveFile(brideBirthProofFile));
            marriageCertificate.setGroomBirthProofPath(marriageCertificateService.saveFile(groomBirthProofFile));
            marriageCertificate.setMarriageAffidavitPath(marriageCertificateService.saveFile(marriageAffidavitFile));

            if (weddingPhotosFile != null && !weddingPhotosFile.isEmpty()) {
                marriageCertificate.setWeddingPhotosPath(marriageCertificateService.saveFile(weddingPhotosFile));
            }

            // Save the application
            MarriageCertificate savedCertificate = marriageCertificateService.saveCertificate(marriageCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading files. Please try again.");
            return "redirect:/apply-certificate";
        }
    }

    //ADMIN AND APPLICANT INCOME CERTIFICATE VIEW DETAILS
    @GetMapping("/user-view-marrriage-cetificate/{id}")
    public String UserGetApplicationDetails(@PathVariable Long id, Model model)
    {
        MarriageCertificate marriageCert=marriageCertificateService.getCertificateById(id);
        if(marriageCert==null)
        {
            return "error/not-found";
        }
        model.addAttribute("details",marriageCert);
        return "user-view-marriage-applications-details";
    }
    @GetMapping("/admin-view-applications/marriage/{id}")
    public String adminGetApplicationDetails(@PathVariable Long id,Model model)
    {
        MarriageCertificate marriageCert=marriageCertificateService.getCertificateById(id);
        model.addAttribute("details",marriageCert);
        return "admin-view-marriage-applications-details";
    }

    @GetMapping("/download/marriage/{id}")
    public ResponseEntity<byte[]> downloadBirthCertificate(@PathVariable Long id) {
        try {
            MarriageCertificate certificate = marriageCertificateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateMarriageCertificatePdf(
                    certificate.getBrideFullName(),
                    certificate.getGroomFullName(),
//                    String.valueOf(certificate.getDateOfBirth()),
                    certificate.getBrideFatherName()
            );

            String filename = "MarriageCertificate_" + id + ".pdf";

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
