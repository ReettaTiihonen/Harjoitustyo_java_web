package net.dancer.jump.taskmanagement.service;

import net.dancer.jump.taskmanagement.domain.Participant;
import net.dancer.jump.taskmanagement.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    private final ParticipantRepository repository;

    public ParticipantService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public Iterable<Participant> findAll() {
        return repository.findAll();
    }

    public Participant save(Participant p) {
        return repository.save(p);
    }

    public void delete(Participant p) {
        repository.delete(p);
    }
}
