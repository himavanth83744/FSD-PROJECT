package com.veltech.portal.repository;

import com.veltech.portal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for Event entity.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCategory(String category);

    List<Event> findByStatus(String status);

    List<Event> findByCategoryAndStatus(String category, String status);

    List<Event> findByTitleContainingIgnoreCase(String keyword);
}
