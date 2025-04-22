package net.dancer.jump.taskmanagement.repository;

import net.dancer.jump.taskmanagement.domain.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
