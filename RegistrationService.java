package com.veltech.portal.service;

import com.veltech.portal.model.Registration;
import com.veltech.portal.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for event Registrations.
 */
@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public Registration register(Registration reg) {
        // Prevent duplicate registrations
        if (registrationRepository.existsByRollNumberIgnoreCaseAndEventName(
                reg.getRollNumber(), reg.getEventName())) {
            throw new RuntimeException("Already registered for this event.");
        }
        reg.setRegisteredAt(LocalDateTime.now());
        reg.setStatus("confirmed");
        return registrationRepository.save(reg);
    }

    public List<Registration> getByRollNumber(String rollNumber) {
        return registrationRepository.findByRollNumberIgnoreCase(rollNumber);
    }

    public List<Registration> getByEvent(String eventName) {
        return registrationRepository.findByEventName(eventName);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> getById(Long id) {
        return registrationRepository.findById(id);
    }

    public Registration cancelRegistration(Long id) {
        return registrationRepository.findById(id).map(r -> {
            r.setStatus("cancelled");
            return registrationRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Registration not found: " + id));
    }

    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

    public long countByEvent(String eventName) {
        return registrationRepository.countByEventName(eventName);
    }
}
