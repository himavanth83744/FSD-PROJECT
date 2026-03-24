package com.veltech.portal.repository;

import com.veltech.portal.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for Registration entity.
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByRollNumberIgnoreCase(String rollNumber);

    List<Registration> findByEventName(String eventName);

    boolean existsByRollNumberIgnoreCaseAndEventName(String rollNumber, String eventName);

    long countByEventName(String eventName);
}
