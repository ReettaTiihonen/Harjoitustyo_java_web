package net.dancer.jump.taskmanagement.repository;

import net.dancer.jump.taskmanagement.domain.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
