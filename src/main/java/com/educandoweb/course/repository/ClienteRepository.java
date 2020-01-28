package com.educandoweb.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
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
	
	@EntityGraph(attributePaths = "authorities")
	@Query("from cliente c where c.user.login = :login and c.user.authorites is not null")
    Optional<Cliente> findOneWithAuthoritiesByLogin(@Param("login") String login);
}