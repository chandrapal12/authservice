package org.example.repository;

import org.example.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
public User findByUsername(String username);
}
