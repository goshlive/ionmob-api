package com.ionmob.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionmob.model.User;

/**
 * JPA Repository signature of the tb_user table
 * 
 * @author I Made Putrama
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
