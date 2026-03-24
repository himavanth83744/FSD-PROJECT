package com.veltech.portal.service;

import com.veltech.portal.model.Event;
import com.veltech.portal.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Business logic for Event management.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    public List<Event> searchEvents(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updated) {
        return eventRepository.findById(id).map(ev -> {
            ev.setTitle(updated.getTitle());
            ev.setCategory(updated.getCategory());
            ev.setDescription(updated.getDescription());
            ev.setDate(updated.getDate());
            ev.setTime(updated.getTime());
            ev.setVenue(updated.getVenue());
            ev.setOrganizer(updated.getOrganizer());
            ev.setSeats(updated.getSeats());
            ev.setPrize(updated.getPrize());
            ev.setStatus(updated.getStatus());
            ev.setTags(updated.getTags());
            return eventRepository.save(ev);
        }).orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Event incrementRegistered(Long id) {
        return eventRepository.findById(id).map(ev -> {
            ev.setRegistered(ev.getRegistered() + 1);
            if (ev.getRegistered() >= ev.getSeats()) ev.setStatus("closed");
            else if (ev.getRegistered() >= ev.getSeats() * 0.85) ev.setStatus("filling");
            return eventRepository.save(ev);
        }).orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }
}
