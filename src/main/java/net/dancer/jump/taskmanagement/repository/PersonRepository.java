package net.dancer.jump.taskmanagement.repository;

import net.dancer.jump.taskmanagement.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
