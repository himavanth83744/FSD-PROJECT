package com.veltech.portal.controller;

import com.veltech.portal.model.Registration;
import com.veltech.portal.service.EventService;
import com.veltech.portal.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Registrations API.
 * Base URL: /api/registrations
 */
@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private EventService eventService;

    // POST /api/registrations — register for event
    @PostMapping
    public ResponseEntity<?> register(@RequestBody Registration reg) {
        try {
            Registration saved = registrationService.register(reg);
            // Increment event seat count
            eventService.getEventById(
                eventService.getAllEvents().stream()
                    .filter(e -> e.getTitle().equals(reg.getEventName()))
                    .findFirst()
                    .map(e -> e.getId()).orElse(-1L)
            ).ifPresent(ev -> eventService.incrementRegistered(ev.getId()));
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/registrations — all (admin)
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    // GET /api/registrations/student/{rollNumber}
    @GetMapping("/student/{rollNumber}")
    public List<Registration> getByStudent(@PathVariable String rollNumber) {
        return registrationService.getByRollNumber(rollNumber);
    }

    // GET /api/registrations/event/{eventName}
    @GetMapping("/event/{eventName}")
    public List<Registration> getByEvent(@PathVariable String eventName) {
        return registrationService.getByEvent(eventName);
    }

    // GET /api/registrations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getById(@PathVariable Long id) {
        return registrationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/registrations/{id}/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Registration> cancel(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(registrationService.cancelRegistration(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/registrations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registrationService.cancelRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
