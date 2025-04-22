package net.dancer.jump.taskmanagement.service;

import net.dancer.jump.taskmanagement.domain.Address;
import net.dancer.jump.taskmanagement.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Iterable<Address> findAll() {
        return repository.findAll();
    }

    public Address save(Address address) {
        return repository.save(address);
    }

    public void delete(Address address) {
        repository.delete(address);
    }

    public Optional<Address> findById(Long id) {
        return repository.findById(id);
    }
}
