package com.veltech.portal.config;

import com.veltech.portal.model.Event;
import com.veltech.portal.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds the database with sample events on first run.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(String... args) {
        if (eventRepository.count() > 0) return; // already seeded

        eventRepository.save(new Event(
            "Tech Symposium 2025", "Technical",
            "A flagship technical event featuring paper presentations, project expos, and keynote speeches from industry leaders in AI and ML.",
            "🖥️", "#1A1A3E", LocalDate.of(2025, 4, 12), "09:00 AM",
            "Main Auditorium", "CSE Dept.", 300, 178, "₹50,000 Prize Pool",
            "open", "Paper Presentation,AI/ML,Project Expo"
        ));
        eventRepository.save(new Event(
            "Hackathon X – Code Blitz", "Hackathon",
            "24-hour hackathon challenging teams to build innovative solutions for real-world problems. All domains welcome!",
            "⚡", "#1A1E2E", LocalDate.of(2025, 4, 19), "08:00 AM",
            "Innovation Hub", "IT Dept.", 200, 190, "₹1,00,000 Prizes",
            "filling", "24-Hour,Team Event,Open Domain"
        ));
        eventRepository.save(new Event(
            "Vel Fest – Cultural Extravaganza", "Cultural",
            "Annual cultural extravaganza with dance, music, drama, and art competitions. Celebrate diversity and talent.",
            "🎭", "#2E1A1A", LocalDate.of(2025, 5, 3), "05:00 PM",
            "Open Air Theatre", "Student Council", 1000, 340, "₹25,000 Prizes",
            "open", "Dance,Music,Drama,Art"
        ));
        eventRepository.save(new Event(
            "IoT & Robotics Workshop", "Workshop",
            "Hands-on workshop covering IoT fundamentals, sensor integration, and robotics programming using Arduino and Raspberry Pi.",
            "🤖", "#1A2E1A", LocalDate.of(2025, 4, 25), "10:00 AM",
            "ECE Lab Complex", "ECE Dept.", 60, 60, "Certificate + Kit",
            "closed", "Hands-on,Arduino,Raspberry Pi"
        ));
        eventRepository.save(new Event(
            "Sports Meet 2025", "Sports",
            "Annual inter-department sports meet featuring cricket, football, basketball, badminton, athletics and more.",
            "🏆", "#1E2A1A", LocalDate.of(2025, 5, 15), "07:00 AM",
            "Sports Complex", "Physical Ed. Dept.", 500, 210, "Medals + Trophies",
            "open", "Cricket,Football,Athletics"
        ));
        eventRepository.save(new Event(
            "AI for Good — Ideathon", "Technical",
            "Ideathon focused on leveraging AI for social good – from healthcare to agriculture to education.",
            "🧠", "#1A1A3E", LocalDate.of(2025, 6, 1), "10:00 AM",
            "Seminar Hall A", "AI & ML Dept.", 120, 32, "₹30,000 Prizes",
            "upcoming", "AI/ML,Social Impact,Ideas"
        ));
        eventRepository.save(new Event(
            "Business Plan Competition", "Technical",
            "Present your startup idea to a panel of investors and industry mentors. Winner gets funding and incubation support.",
            "💼", "#2A1E0E", LocalDate.of(2025, 5, 20), "11:00 AM",
            "Seminar Hall B", "Business School", 80, 55, "₹20,000 + Mentorship",
            "open", "Startup,Pitch,Entrepreneurship"
        ));
        eventRepository.save(new Event(
            "Photography & Design Fest", "Cultural",
            "Showcase your photography, graphic design, and digital art skills. Winners get exhibited in the university gallery.",
            "📸", "#2E1A2E", LocalDate.of(2025, 5, 8), "09:00 AM",
            "Art Gallery Hall", "Fine Arts Club", 150, 88, "₹10,000 + Exhibition",
            "open", "Photography,Design,Art"
        ));

        System.out.println("✅ Veltech Portal: Sample events seeded successfully.");
    }
}
