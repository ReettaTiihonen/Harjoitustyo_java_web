package net.dancer.jump.taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import net.dancer.jump.taskmanagement.domain.Address;
public interface AddressRepository extends CrudRepository<Address, Long> {
}
