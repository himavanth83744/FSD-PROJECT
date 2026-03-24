package com.veltech.portal.controller;

import com.veltech.portal.model.Event;
import com.veltech.portal.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Events API.
 * Base URL: /api/events
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // GET /api/events — list all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET /api/events/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/events/category/{cat}
    @GetMapping("/category/{category}")
    public List<Event> getByCategory(@PathVariable String category) {
        return eventService.getEventsByCategory(category);
    }

    // GET /api/events/search?keyword=xyz
    @GetMapping("/search")
    public List<Event> searchEvents(@RequestParam String keyword) {
        return eventService.searchEvents(keyword);
    }

    // POST /api/events — create (admin)
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    // PUT /api/events/{id} — update (admin)
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updated) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/events/{id} — delete (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
