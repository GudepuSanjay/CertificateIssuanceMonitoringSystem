package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.*;
import com.example.CertificateIssuanceMonitoringSystem.repository.BirthCertficateRepository;
import com.example.CertificateIssuanceMonitoringSystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.CertificateIssuanceMonitoringSystem.model.BirthCertificate;

import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BirthCertificateService birthCertificateService;

    @Autowired
    private IncomeCertificateService incomeCertificateService;

    @Autowired
    private ResidenceCertificateService residenceCertificateService;

    @Autowired
    private CasteCertificateService casteCertificateService;

    @Autowired
    private DeathCertificateService deathCertificateService;

    @Autowired
    private MarriageCertificateService marriageCertificateService;

    @Autowired
    private UserService userService;

    @Autowired
    private BirthCertficateRepository birthCertficateRepository;


    @GetMapping("/admin-view-applications")
    public String getAdminViewApplications(Authentication authentication, Model model) {
        List<BirthCertificate> birthCert = birthCertificateService.getAllBirthCertificates();
        List<IncomeCertificate> incomeCert= incomeCertificateService.getAllIncomeCertificates();
        List<ResidenceCertificate> residenceCert=residenceCertificateService.getALlResidenceCertificates();
        List<MarriageCertificate> marriageCert=marriageCertificateService.getAllMarriageCertificates();
        List<CasteCertificate> casteCert=casteCertificateService.getAllCasteCertificates();
        List<DeathCertificate> deathCert=deathCertificateService.getAllDeathCertificates();

        model.addAttribute("birthCertificate",birthCert != null ? birthCert : Collections.emptyList());
        model.addAttribute("incomeCertificate",incomeCert != null ? incomeCert : Collections.emptyList());
        model.addAttribute("casteCertificate",casteCert != null ? casteCert : Collections.emptyList());
        model.addAttribute("residenceCertificate",residenceCert != null ? residenceCert : Collections.emptyList());
        model.addAttribute("marriageCertificate",marriageCert != null ? marriageCert : Collections.emptyList());
        model.addAttribute("deathCertificate",deathCert != null ? deathCert : Collections.emptyList());
        model.addAttribute("totalApprovedApplications",birthCertificateService.totalByStatus("Approved"));
        model.addAttribute("totalRejectedApplications",birthCertificateService.totalByStatus("Rejected"));
        model.addAttribute("username",authentication.getName());
        return "admin-view-applications";
    }


    @GetMapping("/approved-applications")
    public String approvedApplications(Authentication authentication,Model model)
    {
        List<BirthCertificate> approvedApplications =birthCertificateService.getApplicationsByStatus("Approved");
        model.addAttribute("applications",approvedApplications);
        model.addAttribute("username",authentication.getName());
        model.addAttribute("totalApprovedApplications",birthCertificateService.totalByStatus("Approved"));
        model.addAttribute("totalRejectedApplications",birthCertificateService.totalByStatus("Rejected"));
//        model.addAttribute("certificateType",birthCertificateService.typeOfCertificate());
        return "approved-applications";
    }
    @GetMapping("/rejected-applications")
    public String rejectedApplications(Authentication authentication,Model model)
    {
        List<BirthCertificate> rejectedapplications =birthCertificateService.getApplicationsByStatus("Rejected");
        model.addAttribute("applications",rejectedapplications);
        model.addAttribute("username",authentication.getName());
        model.addAttribute("totalApprovedApplications",birthCertificateService.totalByStatus("Approved"));
        model.addAttribute("totalRejectedApplications",birthCertificateService.totalByStatus("Rejected"));
        return "rejected-applications";

    }

@PostMapping("/update-status")
public String updateStatus(
        @RequestParam Long id,
        @RequestParam String status,
        @RequestParam String type,
        @RequestParam String remarks
        ) {

        switch (type.toLowerCase()) {
            case "marriage":
                marriageCertificateService.updateStatus(id, status, remarks);
                break;
            case "caste":
                casteCertificateService.updateStatus(id, status, remarks);
                break;
            case "income":
                incomeCertificateService.updateStatus(id, status, remarks);
                break;
            case "residence":
                residenceCertificateService.updateStatus(id, status, remarks);
                break;
            case "birth":
                birthCertificateService.updateStatus(id, status, remarks);
                break;
            case "death":
                deathCertificateService.updateStatus(id, status, remarks);
                break;
            default:
                throw new IllegalArgumentException("Invalid Certificate Type: " + type);
        }
            return "redirect:/admin-view-applications";
        }

//    @GetMapping("/update-profile")
//    public String showProfileForm(@AuthenticationPrincipal User user, Model model) {
//        model.addAttribute("profile", new ProfileUpdateDto());
//        model.addAttribute("user", user);
//        return "profile";
//    }

//
//    @PostMapping("/profile/update")
//    public String updateProfile(
//            @AuthenticationPrincipal User user,
//            @ModelAttribute("profile") @Valid ProfileUpdateDto profileDto,
//            BindingResult result,
//            RedirectAttributes redirectAttributes) {
//
//        // Add null check for authenticated user
//        if (user == null) {
//            redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to update your profile.");
//            return "redirect:/login";
//        }
//
//        if (result.hasErrors()) {
//            return "profile";
//        }
//
//        try {
//            userService.updateProfile(user.getUsername(), profileDto);
//            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
//            return "profile";
//        }
//
//        return "redirect:/profile";
//    }
}

