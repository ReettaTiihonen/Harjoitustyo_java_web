package net.dancer.jump.taskmanagement.service;

import net.dancer.jump.taskmanagement.domain.Event;
import net.dancer.jump.taskmanagement.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Iterable<Event> findAll() {
        return repository.findAll();
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }
}
