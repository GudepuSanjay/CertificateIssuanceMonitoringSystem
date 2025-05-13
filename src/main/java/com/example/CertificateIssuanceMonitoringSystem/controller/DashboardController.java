//package com.example.authproject.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class DashboardController {
//    @GetMapping("/admin-dashboard")
//    public String adminDashboard(Authentication authentication, Model model) {
//        model.addAttribute("username", authentication.getName());
//        return "admin-dashboard";
//    }
//
//    @GetMapping("/applicant-dashboard")
//    public String applicantDashboard(Authentication authentication, Model model) {
//        model.addAttribute("username", authentication.getName());
//        return "applicant-dashboard";
//    }
//}

package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {
    @Autowired
    private BirthCertificateService birthCertificateService;
    @Autowired
    private UserService userService;
    @Autowired
    private IncomeCertificateService incomeCertificateService;
    @Autowired
    private CasteCertificateService casteCertificateService;
    @Autowired
    private ResidenceCertificateService residenceCertificateService;
    @Autowired
    private DeathCertificateService deathCertificateService;
    @Autowired
    private MarriageCertificateService marriageCertificateService;

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
//        List<BirthCertificate> applications =birthCertificateService.getAllBirthCertificates();
//        model.addAttribute("applications",applications);
        model.addAttribute("totalUsers",userService.countUsers());


        model.addAttribute("totalApprovedApplications",birthCertificateService.totalByStatus("Approved"));
        model.addAttribute("totalRejectedApplications",birthCertificateService.totalByStatus("Rejected"));
        model.addAttribute("totalPendingApplications",birthCertificateService.totalByStatus("Pending"));

        model.addAttribute("approvedBirthStatus",birthCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedBirthStatus",birthCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingBirthStatus",birthCertificateService.countByStatus("Pending"));

        model.addAttribute("approvedIncomeStatus",incomeCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedIncomeStatus",incomeCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingIncomeStatus",incomeCertificateService.countByStatus("Pending"));

        model.addAttribute("approvedCasteStatus",casteCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedCasteStatus",casteCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingCasteStatus",casteCertificateService.countByStatus("Pending"));

        model.addAttribute("approvedResidenceStatus",residenceCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedResidenceStatus",residenceCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingResidenceStatus",residenceCertificateService.countByStatus("Pending"));

        model.addAttribute("approvedMarriageStatus",marriageCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedMarriageStatus",marriageCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingMarriageStatus",marriageCertificateService.countByStatus("Pending"));

        model.addAttribute("approvedDeathStatus",deathCertificateService.countByStatus("Approved"));
        model.addAttribute("rejectedDeathStatus",deathCertificateService.countByStatus("Rejected"));
        model.addAttribute("pendingDeathStatus",deathCertificateService.countByStatus("Pending"));



        model.addAttribute("incomeCertificateApplications",incomeCertificateService.countApplications());
        model.addAttribute("casteCertificateApplications",casteCertificateService.countApplications());
        model.addAttribute("residenceCertificateApplications",residenceCertificateService.countApplications());
        model.addAttribute("marriageCertificateApplications",marriageCertificateService.countApplications());
        model.addAttribute("birthCertificateApplications",birthCertificateService.countApplications());
        model.addAttribute("deathCertificateApplications",deathCertificateService.countApplications());

        model.addAttribute("totalCertificatesApplications",birthCertificateService.totalCertificatesApplications());
        return "admin-dashboard";
    }
    @GetMapping("/certificate-counts")
    public Map<String, Long> getCertificateCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("birthCount", birthCertificateService.countApplications());
        counts.put("incomeCount", incomeCertificateService.countApplications());
        counts.put("casteCount", 20L);
        return counts;
    }

    @GetMapping("/applicant-dashboard")
    public String applicantDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("totalApprovedApplications",birthCertificateService.totalByStatus("Approved"));
        model.addAttribute("totalRejectedApplications",birthCertificateService.totalByStatus("Rejected"));
        model.addAttribute("totalPendingApplications",birthCertificateService.totalByStatus("Pending"));

        model.addAttribute("incomeCertificateApplications",incomeCertificateService.countApplications());
        model.addAttribute("casteCertificateApplications",casteCertificateService.countApplications());
        model.addAttribute("residenceCertificateApplications",residenceCertificateService.countApplications());
        model.addAttribute("marriageCertificateApplications",marriageCertificateService.countApplications());
        model.addAttribute("birthCertificateApplications",birthCertificateService.countApplications());
        model.addAttribute("deathCertificateApplications",deathCertificateService.countApplications());


        return "applicant-dashboard";
    }

//    @GetMapping("/admin-view-applications")
//    public String getAdminViewApplications(Model model)
//    {
//        List<BirthCertificate> applications =birthCertificateService.getAllBirthCertificates();
//        model.addAttribute("applications",applications);
//        return "admin-view-applications";
//    }
//
//    @GetMapping("/approved-applications")
//    public String approvedApplications(Model model)
//    {
//        List<BirthCertificate> applications =birthCertificateService.getApplicationsByStatus("Approved");
//        model.addAttribute("applications",applications);
//        return "approved-applications";
//    }
//    @GetMapping("/rejected-applications")
//    public String rejectedApplications(Model model)
//    {
//        List<BirthCertificate> applications =birthCertificateService.getApplicationsByStatus("Approved");
//        model.addAttribute("applications",applications);
//        return "rejected-applications";
//    }
}
