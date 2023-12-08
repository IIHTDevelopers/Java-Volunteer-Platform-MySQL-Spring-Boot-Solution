package com.volunteerplatform.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.volunteerplatform.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
