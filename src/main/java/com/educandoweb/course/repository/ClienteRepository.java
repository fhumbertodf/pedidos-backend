package com.educandoweb.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Cliente;


/**
 * Spring Data  repository for the Cliente entity.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findOneByEmailIgnoreCase(String email);
	
	@Query("select c from Cliente c join c.user.authorities where c.email = :email")
	Optional<Cliente> findOneWithAuthoritiesByEmailIgnoreCase(@Param("email") String email);
}