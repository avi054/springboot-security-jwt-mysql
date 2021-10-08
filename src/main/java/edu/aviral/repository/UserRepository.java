package edu.aviral.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.aviral.dao.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
