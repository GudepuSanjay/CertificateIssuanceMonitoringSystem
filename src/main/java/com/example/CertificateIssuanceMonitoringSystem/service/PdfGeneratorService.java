package com.example.CertificateIssuanceMonitoringSystem.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {


    private static final float MARGIN = 50;
    private static final float LINE_SPACING = 20;
    private static final float TITLE_FONT_SIZE = 16;
    private static final float BODY_FONT_SIZE = 12;
    private static final float HEADER_FONT_SIZE = 14;

    private final TemplateEngine templateEngine;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generateBirthCertificatePdf(String fullName, String birthPlace, String dateOfBirth, String fatherName, String gender, String religion, String caste, String applicationDate, Long id) {
        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("birthPlace", birthPlace);
            context.setVariable("dateOfBirth", dateOfBirth);
            context.setVariable("fatherName", fatherName);
            context.setVariable("gender", gender);
            context.setVariable("religion", religion);
            context.setVariable("caste", caste);
            context.setVariable("applicationDate",applicationDate);
            context.setVariable("id",id);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("birth-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateIncomeCertificatePdf(String fullName, String birthPlace, String dateOfBirth, String fatherName) {
        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("birthPlace", birthPlace);
            context.setVariable("dateOfBirth", dateOfBirth);
            context.setVariable("fatherName", fatherName);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("income-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateCasteCertificatePdf(String fullName, String fatherName, String dateOfBirth, String motherName) {
        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("fatherName", fatherName);
            context.setVariable("dateOfBirth", dateOfBirth);
            context.setVariable("motherName", fatherName);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("caste-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateMarriageCertificatePdf(String brideFullName, String groomFullName, String brideFatherName) {
        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", brideFullName);
            context.setVariable("fatherName", groomFullName);

            context.setVariable("motherName", brideFatherName);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("marriage-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateResidenceCertificatePdf(String fullName, Long id, String fatherName, String gender, String district, String state, String nationality, String certificateNationality) {
        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("fatherName", fatherName);

            context.setVariable("gender", gender);
            context.setVariable("id", id);
            context.setVariable("district", district);
            context.setVariable("state", state);
            context.setVariable("nationality", nationality);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("residence-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public byte[] generateDeathCertificatePdf(String deceasedName, Long id, String s, String causeOfDeath, Integer ageAtDeath, String deathPlaceAddress, String fatherOrHusbandName, String permanentAddress) {

        try {
            // ✅ Create a Thymeleaf context and set dynamic values
            Context context = new Context();
            context.setVariable("fullName", deceasedName);
            context.setVariable("fatherName", fatherOrHusbandName);

            context.setVariable("causeDeath", causeOfDeath);
            context.setVariable("id", id);
            context.setVariable("ageAtDeath", ageAtDeath);
            context.setVariable("deathPlace", deathPlaceAddress);
            context.setVariable("address",permanentAddress);

            // ✅ Process the HTML template with Thymeleaf
            String htmlContent = templateEngine.process("death-certificate-template", context);

            // ✅ Debugging: Print HTML content before PDF generation
            System.out.println("Generated HTML for PDF:");
            System.out.println(htmlContent);

            // ✅ Convert HTML to PDF with proper Bootstrap & CSS handling
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                // Ensure styles are rendered properly
                renderer.layout();
                renderer.createPDF(outputStream);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error to console
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}



