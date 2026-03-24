-- ═══════════════════════════════════════════════════════
--  VELTECH UNIVERSITY — CAMPUS EVENT & REGISTRATION PORTAL
--  MySQL Database Schema
--  Run this ONCE to initialize the database
-- ═══════════════════════════════════════════════════════

CREATE DATABASE IF NOT EXISTS veltech_portal
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE veltech_portal;

-- ─────────────────────────────────────
-- TABLE: events
-- ─────────────────────────────────────
CREATE TABLE IF NOT EXISTS events (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200)   NOT NULL,
    category    VARCHAR(100)   NOT NULL,
    description TEXT,
    emoji       VARCHAR(10),
    color       VARCHAR(20),
    date        DATE,
    time        VARCHAR(20),
    venue       VARCHAR(200),
    organizer   VARCHAR(200),
    seats       INT DEFAULT 100,
    registered  INT DEFAULT 0,
    prize       VARCHAR(200),
    status      ENUM('open','filling','closed','upcoming') DEFAULT 'open',
    tags        VARCHAR(500),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category),
    INDEX idx_status (status)
) ENGINE=InnoDB;

-- ─────────────────────────────────────
-- TABLE: registrations
-- ─────────────────────────────────────
CREATE TABLE IF NOT EXISTS registrations (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(200)  NOT NULL,
    roll_number     VARCHAR(50)   NOT NULL,
    department      VARCHAR(200),
    year            VARCHAR(20),
    email           VARCHAR(200)  NOT NULL,
    phone           VARCHAR(20),
    event_name      VARCHAR(200)  NOT NULL,
    message         TEXT,
    status          ENUM('confirmed','pending','cancelled') DEFAULT 'confirmed',
    registered_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_student_event (roll_number, event_name),
    INDEX idx_roll   (roll_number),
    INDEX idx_event  (event_name)
) ENGINE=InnoDB;

-- ─────────────────────────────────────
-- TABLE: users
-- ─────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(200)  NOT NULL,
    roll_number VARCHAR(50)   NOT NULL UNIQUE,
    email       VARCHAR(200)  NOT NULL UNIQUE,
    password    VARCHAR(255)  NOT NULL,
    role        ENUM('STUDENT','ADMIN') DEFAULT 'STUDENT',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ─────────────────────────────────────
-- SAMPLE DATA: Events
-- ─────────────────────────────────────
INSERT INTO events (title, category, description, emoji, color, date, time, venue, organizer, seats, registered, prize, status, tags) VALUES
('Tech Symposium 2025',          'Technical', 'A flagship technical event featuring paper presentations, project expos, and keynote speeches from industry leaders in AI and ML.', '🖥️', '#1A1A3E', '2025-04-12', '09:00 AM', 'Main Auditorium',  'CSE Dept.',        300,  178, '₹50,000 Prize Pool',     'open',     'Paper Presentation,AI/ML,Project Expo'),
('Hackathon X – Code Blitz',     'Hackathon', '24-hour hackathon challenging teams to build innovative solutions for real-world problems. All domains welcome!',                  '⚡', '#1A1E2E', '2025-04-19', '08:00 AM', 'Innovation Hub',    'IT Dept.',         200,  190, '₹1,00,000 Prizes',       'filling',  '24-Hour,Team Event,Open Domain'),
('Vel Fest – Cultural Extravaganza','Cultural','Annual cultural extravaganza with dance, music, drama, and art competitions.',                                                    '🎭', '#2E1A1A', '2025-05-03', '05:00 PM', 'Open Air Theatre',  'Student Council', 1000,  340, '₹25,000 Prizes',         'open',     'Dance,Music,Drama,Art'),
('IoT & Robotics Workshop',       'Workshop', 'Hands-on workshop covering IoT fundamentals, sensor integration, and robotics programming using Arduino and Raspberry Pi.',       '🤖', '#1A2E1A', '2025-04-25', '10:00 AM', 'ECE Lab Complex',   'ECE Dept.',         60,   60, 'Certificate + Kit',      'closed',   'Hands-on,Arduino,Raspberry Pi'),
('Sports Meet 2025',              'Sports',   'Annual inter-department sports meet featuring cricket, football, basketball, badminton, athletics and more.',                      '🏆', '#1E2A1A', '2025-05-15', '07:00 AM', 'Sports Complex',    'Physical Ed.',     500,  210, 'Medals + Trophies',      'open',     'Cricket,Football,Athletics'),
('AI for Good — Ideathon',        'Technical','Ideathon focused on leveraging AI for social good – healthcare, agriculture, education.',                                          '🧠', '#1A1A3E', '2025-06-01', '10:00 AM', 'Seminar Hall A',    'AI & ML Dept.',    120,   32, '₹30,000 Prizes',         'upcoming', 'AI/ML,Social Impact,Ideas'),
('Business Plan Competition',     'Technical','Present your startup idea to a panel of investors and industry mentors.',                                                          '💼', '#2A1E0E', '2025-05-20', '11:00 AM', 'Seminar Hall B',    'Business School',   80,   55, '₹20,000 + Mentorship',   'open',     'Startup,Pitch,Entrepreneurship'),
('Photography & Design Fest',     'Cultural', 'Showcase your photography, graphic design, and digital art skills.',                                                              '📸', '#2E1A2E', '2025-05-08', '09:00 AM', 'Art Gallery Hall',  'Fine Arts Club',   150,   88, '₹10,000 + Exhibition',   'open',     'Photography,Design,Art');

-- ─────────────────────────────────────
-- ADMIN USER (password: admin@veltech)
-- BCrypt hash of "admin@veltech"
-- ─────────────────────────────────────
INSERT INTO users (full_name, roll_number, email, password, role) VALUES
('Admin User', 'ADMIN001', 'admin@veltech.edu.in', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh7y', 'ADMIN');

SELECT 'Veltech Portal database initialized successfully! ✅' AS Status;
