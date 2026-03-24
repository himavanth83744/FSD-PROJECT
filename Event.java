package com.veltech.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Event entity mapped to the `events` table.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String description;

    private String emoji;
    private String color;
    private LocalDate date;
    private String time;
    private String venue;
    private String organizer;
    private int seats;
    private int registered;
    private String prize;
    private String status;     // open | filling | closed | upcoming
    private String tags;       // comma-separated

    // ── Constructors ──
    public Event() {}

    public Event(String title, String category, String description, String emoji,
                 String color, LocalDate date, String time, String venue,
                 String organizer, int seats, int registered, String prize,
                 String status, String tags) {
        this.title       = title;
        this.category    = category;
        this.description = description;
        this.emoji       = emoji;
        this.color       = color;
        this.date        = date;
        this.time        = time;
        this.venue       = venue;
        this.organizer   = organizer;
        this.seats       = seats;
        this.registered  = registered;
        this.prize       = prize;
        this.status      = status;
        this.tags        = tags;
    }

    // ── Getters & Setters ──
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getRegistered() { return registered; }
    public void setRegistered(int registered) { this.registered = registered; }

    public String getPrize() { return prize; }
    public void setPrize(String prize) { this.prize = prize; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}
