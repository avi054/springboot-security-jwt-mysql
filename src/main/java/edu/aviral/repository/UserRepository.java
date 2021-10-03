package edu.aviral.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.aviral.dao.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
