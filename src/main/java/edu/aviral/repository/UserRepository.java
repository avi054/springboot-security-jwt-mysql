package edu.aviral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.aviral.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
