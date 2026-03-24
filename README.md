# 🎓 Veltech University — Campus Event & Registration Portal

A full-stack web application for managing academic campus events, registrations, and student participation at Veltech University.

---

## 🏗️ Tech Stack

| Layer       | Technology              |
|-------------|-------------------------|
| Frontend    | HTML5, CSS3, JavaScript |
| Backend     | Java 17 + Spring Boot 3 |
| Database    | MySQL 8.0               |
| ORM         | Spring Data JPA/Hibernate |
| Security    | Spring Security + BCrypt |
| Build Tool  | Maven                   |

---

## 📁 Project Structure

```
veltech-portal/
├── frontend/
│   ├── index.html        ← Main portal page
│   ├── style.css         ← Gold & white premium theme
│   └── app.js            ← Frontend logic + API integration
│
├── backend/
│   ├── pom.xml           ← Maven dependencies
│   └── src/main/
│       ├── java/com/veltech/portal/
│       │   ├── VeltechPortalApplication.java   ← Entry point
│       │   ├── model/
│       │   │   ├── Event.java
│       │   │   ├── Registration.java
│       │   │   └── User.java
│       │   ├── repository/
│       │   │   ├── EventRepository.java
│       │   │   ├── RegistrationRepository.java
│       │   │   └── UserRepository.java
│       │   ├── service/
│       │   │   ├── EventService.java
│       │   │   └── RegistrationService.java
│       │   ├── controller/
│       │   │   ├── EventController.java
│       │   │   ├── RegistrationController.java
│       │   │   └── AuthController.java
│       │   └── config/
│       │       ├── SecurityConfig.java
│       │       └── DataInitializer.java
│       └── resources/
│           └── application.properties
│
└── database/
    └── schema.sql        ← MySQL schema + seed data
```

---

## ⚡ Quick Start

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Any modern web browser

---

### 2. Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Run the schema file
source /path/to/veltech-portal/database/schema.sql;
```

Or manually:
```sql
CREATE DATABASE veltech_portal;
USE veltech_portal;
-- then run schema.sql contents
```

---

### 3. Configure Database Password

Edit `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veltech_portal
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD   ← change this
```

---

### 4. Run the Spring Boot Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The API will start at: **http://localhost:8080**

---

### 5. Open the Frontend

Simply open `frontend/index.html` in your browser, or serve it:

```bash
# Using Python (optional)
cd frontend
python -m http.server 3000
# Visit: http://localhost:3000
```

---

## 🌐 REST API Endpoints

### Events
| Method | Endpoint                        | Description          |
|--------|---------------------------------|----------------------|
| GET    | `/api/events`                   | Get all events       |
| GET    | `/api/events/{id}`              | Get event by ID      |
| GET    | `/api/events/category/{cat}`    | Filter by category   |
| GET    | `/api/events/search?keyword=x`  | Search events        |
| POST   | `/api/events`                   | Create event (admin) |
| PUT    | `/api/events/{id}`              | Update event (admin) |
| DELETE | `/api/events/{id}`              | Delete event (admin) |

### Registrations
| Method | Endpoint                              | Description                |
|--------|---------------------------------------|----------------------------|
| POST   | `/api/registrations`                  | Register for an event      |
| GET    | `/api/registrations`                  | Get all registrations      |
| GET    | `/api/registrations/student/{roll}`   | Get by student roll number |
| GET    | `/api/registrations/event/{name}`     | Get by event name          |
| PUT    | `/api/registrations/{id}/cancel`      | Cancel registration        |
| DELETE | `/api/registrations/{id}`             | Delete registration        |

### Auth
| Method | Endpoint           | Description    |
|--------|--------------------|----------------|
| POST   | `/api/auth/signup` | Register user  |
| POST   | `/api/auth/login`  | Login user     |

---

## ✨ Features

- 🎯 **Event Discovery** — Browse events with category filters and live search
- 📝 **Online Registration** — Complete form with validation and duplicate prevention
- 📋 **My Registrations** — Look up registrations by roll number
- ❌ **Cancel Registration** — Students can cancel before event date
- 🔐 **Authentication** — Sign up and login with BCrypt password hashing
- 🌐 **Offline Mode** — Falls back to localStorage when API is unavailable
- 📱 **Responsive** — Works on desktop, tablet, and mobile
- 🏆 **Admin Seeding** — Sample events auto-seeded on first run

---

## 🎨 Design

- **Theme:** Dark background with Gold (#C9A84C) and White accents
- **Fonts:** Playfair Display (headings) + DM Sans (body)
- **Animations:** Smooth fade-up, hover transforms, toast notifications

---

## 🔐 Default Admin

| Field    | Value                  |
|----------|------------------------|
| Email    | admin@veltech.edu.in   |
| Password | admin@veltech          |
| Role     | ADMIN                  |

---

## 📞 Contact

**Veltech University**  
No. 42, Avadi-Vel Tech Road, Chennai – 600 062, Tamil Nadu  
📧 events@veltech.edu.in | 📞 +91 44 2684 1601
