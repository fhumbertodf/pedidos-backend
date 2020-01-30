package com.educandoweb.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Cliente;


/**
 * Spring Data  repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findOneByEmailIgnoreCase(String email);
}