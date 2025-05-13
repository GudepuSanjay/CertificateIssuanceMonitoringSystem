package com.example.CertificateIssuanceMonitoringSystem.controller;

import com.example.CertificateIssuanceMonitoringSystem.model.*;
import com.example.CertificateIssuanceMonitoringSystem.service.*;
//import jdk.internal.org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ApplicantController {
    @Autowired
    private BirthCertificateService birthCertificateService;

    @Autowired
    private IncomeCertificateService incomeCertificateService;

    @Autowired
    private MarriageCertificateService marriageCertificateService;

    @Autowired
    private ResidenceCertificateService residenceCertificateService;

    @Autowired
    private DeathCertificateService deathCertificateService;

    @Autowired
    private CasteCertificateService casteCertificateService;
//    private Log log;

    @GetMapping("/apply-certificate")
    public String applyCertificate(Authentication authentication,Model model)
    {
        model.addAttribute("username", authentication.getName());
        return "apply-certificate";
    }


    //GETTING THE OTHER CERTIFICATE FORM AND POSTING, SAVING THE DATA IN BACKEND

    @GetMapping("/other-certificate-form")
    public String showOtherCertificateForm(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "other-certificate-form";
    }



@GetMapping("/view-applications")
public String viewApplications(Authentication authentication,Model model) {
    String username = authentication.getName();
    System.out.println(username);

    List<BirthCertificate> birthCert = birthCertificateService.getAllBirthCertificates();
    List<IncomeCertificate> incomeCert = incomeCertificateService.getAllIncomeCertificates();
    List<MarriageCertificate> marriageCert =marriageCertificateService.getAllMarriageCertificates();
    List<ResidenceCertificate> residenceCert=residenceCertificateService.getALlResidenceCertificates();
    List<DeathCertificate> deathCert= deathCertificateService.getAllDeathCertificates();
    List<CasteCertificate> casteCert= casteCertificateService.getAllCasteCertificates();
    model.addAttribute("birthApplications", birthCert != null ? birthCert : Collections.emptyList());
    model.addAttribute("incomeApplications", incomeCert != null ? incomeCert : Collections.emptyList());
    model.addAttribute("marriageApplications",marriageCert !=null ? marriageCert : Collections.emptyList());
    model.addAttribute("residenceApplications",residenceCert !=null ? residenceCert :Collections.emptyList());
    model.addAttribute("deathApplications",deathCert !=null ? deathCert :Collections.emptyList());
    model.addAttribute("casteApplications",casteCert !=null ? casteCert :Collections.emptyList());
    model.addAttribute("username",username);
    return "view-applications";
}


}
