package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.BirthCertificate;
import com.example.CertificateIssuanceMonitoringSystem.service.IncomeCertificateService;
import com.example.CertificateIssuanceMonitoringSystem.repository.BirthCertficateRepository;
import com.example.CertificateIssuanceMonitoringSystem.repository.IncomeCertificateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.BirthCertificateService;
import com.example.CertificateIssuanceMonitoringSystem.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;


@Controller
public class BirthCertificateController {

    @Autowired
    private BirthCertificateService birthCertificateService;

    @Autowired
    private IncomeCertificateService incomeCertificateService;

    @Autowired
    private BirthCertficateRepository birthCertficateRepository;

    @Autowired
    private IncomeCertificateRepository incomeCertificateRepository;

    @Autowired
    private PdfGeneratorService  pdfGeneratorService;

//    private static final String UPLOAD_DIR = "uploads";

    @GetMapping("/birth-certificate-form")
    public String showBirthCertificateForm(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        BirthCertificate birthCertificate =new BirthCertificate();
        birthCertificate.setApplicationDate(LocalDate.now());
        model.addAttribute("birthcertificate",new BirthCertificate());
        return "birth-certificate-form";
    }

    @PostMapping("/submit-birth-certificate")
    public String submitBirthCertificate(@ModelAttribute BirthCertificate birthCertificate,
                                         @RequestParam("aadharFile") MultipartFile aadharFile,
                                         @RequestParam("photoFile") MultipartFile photoFile,
                                         @RequestParam("birthProofFile") MultipartFile birthProofFile,
                                         @RequestParam("casteProofFile") MultipartFile casteProofFile,
                                         @RequestParam("signatureProofFile") MultipartFile signatureProofFile,
                                         RedirectAttributes redirectAttributes) {
        try {
//             Save the uploaded files
            String aadharPath = birthCertificateService.saveFile(aadharFile);
            String photoPath = birthCertificateService.saveFile(photoFile);
            String casteProofPath=birthCertificateService.saveFile(casteProofFile);
            String birthProofPath=birthCertificateService.saveFile(birthProofFile);
            String signature=birthCertificateService.saveFile(signatureProofFile);

            // Set file paths in BirthCertificate object
            birthCertificate.setAadharCardPath(aadharPath);
            birthCertificate.setPhotoPath(photoPath);
            birthCertificate.setCasteProofPath(casteProofPath);
            birthCertificate.setBirthProofPath(birthProofPath);
            birthCertificate.setSignature(signature);
            birthCertificate.setRemarks("");

            // Save to database
            birthCertificateService.saveCertificate(birthCertificate);

            redirectAttributes.addFlashAttribute("message", "Application submitted successfully!");
            return "redirect:/view-applications";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error uploading files.");
            return "redirect:/apply-certificate";
        }
    }
    //ADMIN AND APPLICANT PREVIEW OF BIRTH CERTIFICATE APPLICATION
    @GetMapping("/user-view-birth-certificate/{id}")
    public String userViewApplicationDetails(@PathVariable Long id, Model model) {
        BirthCertificate birthcertificate= birthCertificateService.getCertificateById(id);
        System.out.println(birthcertificate);
        if (birthcertificate == null) {
            return "error/not-found";
        }
        model.addAttribute("birthCertificate",birthcertificate); // Pass the actual fetched object
        return "user-view-birth-applications-details";
    }


    @GetMapping("/admin-view-birth-certificate/{id}")
    public String adminViewApplicationDetails(@PathVariable Long id, Model model) {
        BirthCertificate application= birthCertificateService.getCertificateById(id);


        if (application == null) {
            model.addAttribute("error", "Application not found.");
            return "view-applications-details"; // Ensure this matches your template name
        }
        System.out.println("Applicant: "+application);
        model.addAttribute("details", application); // Pass the actual fetched object
        return "admin-view-birth-applications-details";
    }

    @GetMapping("/download/birth/{id}")
    public ResponseEntity<byte[]> downloadBirthCertificate(@PathVariable Long id) {
        try {
            BirthCertificate certificate = birthCertficateRepository.getCertificateById(id);

            byte[] pdfBytes = pdfGeneratorService.generateBirthCertificatePdf(
                    certificate.getFullName(),
                    certificate.getBirthPlace(),
                    String.valueOf(certificate.getDateOfBirth()),
                    certificate.getFatherName(),
                    certificate.getGender(),
                    certificate.getCaste(),
                    certificate.getReligion(),
                    String.valueOf(certificate.getApplicationDate()),
                    certificate.getId()
            );

            String filename = "BirthCertificate_" + id + ".pdf";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error generating certificate: " + e.getMessage()).getBytes());
        }
    }


    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();  // Return 404 if the file does not exist
            }

            // Detect the file type (image, pdf, etc.)
            String contentType = "application/octet-stream";  // Default binary type
            if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (filename.endsWith(".pdf")) {
                contentType = "application/pdf";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error
        }
    }
}
