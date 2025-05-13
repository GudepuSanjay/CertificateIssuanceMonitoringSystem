
# 🎓 Certificate Issuance Monitoring System

The **Certificate Issuance Monitoring System** is a web-based application built with **Spring Boot** to streamline the request, review, and issuance of various types of certificates. The system provides two dashboards: one for the **Applicant** and another for the **Admin**, enabling a seamless digital process for managing applications and issuing certificates.

---

## 🚀 Key Features

### 👤 Applicant Dashboard
- Submit applications for:
  - Income Certificate
  - Caste Certificate
  - Residence Certificate
  - Marriage Certificate
  - Birth Certificate
  - Death Certificate
- Track application status:
  - ✅ Approved
  - ❌ Rejected (with remarks)
  - ⏳ Pending
- Download issued certificates after approval.
- View submitted application forms.

### 🛡️ Admin Dashboard
- View and manage all submitted applications.
- Verify, approve, or reject applications with remarks.
- Access:
  - All approved certificates
  - All rejected applications

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate (ORM)
- **Frontend:** HTML, CSS, Thymeleaf
- **Database:** MySQL
- **Build Tool:** Maven

---

## 📁 Project Structure

```
Certificate_Issuance_Monitoring_System/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/certificate/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

Before running the project, update the database configuration in:

`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/certificate_system
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧪 How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/Certificate_Issuance_Monitoring_System.git
cd Certificate_Issuance_Monitoring_System
```

### 2. Build and Run the Project

```bash
./mvnw spring-boot:run
```

Or if you're using a standard Maven installation:

```bash
mvn spring-boot:run
```

### 3. Access the Application

- **Applicant Dashboard:** [http://localhost:8080/applicant](http://localhost:8080/applicant)
- **Admin Dashboard:** [http://localhost:8080/admin](http://localhost:8080/admin)

---

## 📷 Output Screenshots

> Add the following images to the `images/` folder in your repository.

```markdown
![SignUp](images/SignUp_Page.png)
![Login](images/Login_Page.png)
![Applicant Dashboard](images/applicant_dashboard.png)
![Submitted Applications](images/MyApplications_Page.png)
![New Applications](images/NewApplication_Page.png)
![Income Application Form](images/IncomeCertificate_Form.png)
![Caste Application Form](images/CasteCertificate_Form.png)
![Birth Application Form](images/BirthCertificate_Form.png)
![Residence Application Form](images/ResidenceCertificate_Form.png)
![Death Application Form](images/DeathCertificate_Form.png)
![Marriage Application Form](images/MarriageCertificate_Form.png)
![Admin Dashboard](images/admin_dashboard.png)
![View Applications](images/ViewApplications_Page.png)
![Approved Applications](images/ApprovedApplications_Page.png)
![Rejected Applications](images/RejectedApplications_Page.png)
```

---

## 🤝 Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

---

## 📄 License

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
