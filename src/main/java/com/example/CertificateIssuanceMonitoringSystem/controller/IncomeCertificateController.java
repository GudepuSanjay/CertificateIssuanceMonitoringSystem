package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.IncomeCertificate;
import com.example.CertificateIssuanceMonitoringSystem.repository.IncomeCertificateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.IncomeCertificateService;
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
public class IncomeCertificateController {

    @Autowired
    private IncomeCertificateService incomeCertificateService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private IncomeCertificateRepository incomeCertificateRepository;

    
    @GetMapping("/income-certificate-form")
    public String getIncomeCertificateForm(@RequestParam(name = "value",required = false) String name,Model model)
    {
        model.addAttribute("name",name);
        model.addAttribute("incomecertificate",new IncomeCertificate());
        return "income-certificate-form";
    }
    //ADMIN AND APPLICANT INCOME CERTIFICATE VIEW DETAILS
    @GetMapping("/user-view-applications/income/{id}")
    public String UserGetApplicationDetails(@PathVariable Long id,Model model)
    {
        IncomeCertificate incomeCert=incomeCertificateService.getCertificateById(id);
        System.out.println(incomeCert);
        if (incomeCert == null) {
            // Handle not found case
            return "error/not-found";
        }
        model.addAttribute("details",incomeCert);
        return "user-view-income-applications-details";
    }
    @GetMapping("/admin-view-applications/income/{id}")
    public String adminGetApplicationDetails(@PathVariable Long id,Model model)
    {
        IncomeCertificate incomeCert=incomeCertificateService.getCertificateById(id);
        model.addAttribute("details",incomeCert);
        return "admin-view-income-applications-details";
    }

    @GetMapping("/download/income/{id}")
    public ResponseEntity<byte[]> downloadIncomeCertificate(@PathVariable Long id) {
        try {
            IncomeCertificate certificate = incomeCertificateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateIncomeCertificatePdf(
                    certificate.getFullName(),
                    certificate.getAddress(),
                    String.valueOf(certificate.getDateOfBirth()),
                    certificate.getFatherName()
            );

            String filename = "IncomeCertificate_" + id + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating certificate: " + e.getMessage()).getBytes());
        }
    }

    @PostMapping("/submit-income-certificate")
    public String submitIncomeCertificate(@ModelAttribute IncomeCertificate incomeCertificate,
                                         @RequestParam("aadharFile") MultipartFile aadharFile,
                                         @RequestParam("photoFile") MultipartFile photoFile,
                                         @RequestParam("addressProofFile") MultipartFile addressProofFile,
                                         @RequestParam("incomeProofFile") MultipartFile incomeProofFile,
                                          @RequestParam("signatureProofFile") MultipartFile signatureProofFile,
                                         RedirectAttributes redirectAttributes) {
        try {
//             Save the uploaded files
            String aadharCardPath = incomeCertificateService.saveFile(aadharFile);
            String photoPath = incomeCertificateService.saveFile(photoFile);
            String addressProofPath=incomeCertificateService.saveFile(addressProofFile);
            String incomeProofPath=incomeCertificateService.saveFile(incomeProofFile);
            String signature=incomeCertificateService.saveFile(signatureProofFile);


            // Set file paths in BirthCertificate object
            incomeCertificate.setAadharCardPath(aadharCardPath);
            incomeCertificate.setPhotoPath(photoPath);
            incomeCertificate.setAddressProofPath(addressProofPath);
            incomeCertificate.setIncomeProofPath(incomeProofPath);
            incomeCertificate.setSignaturePath(signature);

            incomeCertificate.setRemarks("");
            // Save to database
            incomeCertificateService.saveCertificate(incomeCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading files.");
            return "redirect:/apply-certificate";
        }
    }
}
