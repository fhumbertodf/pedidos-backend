package com.educandoweb.course.repository;

import com.educandoweb.course.domain.Autorizacao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Autorizacao} entity.
 */
public interface AutorizacaoRepository extends JpaRepository<Autorizacao, String> {
}
