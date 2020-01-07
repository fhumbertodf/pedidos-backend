package com.educandoweb.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Cidade;


/**
 * Spring Data  repository for the Cidade entity.
 */

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public Page<Cidade> findByEstado(@Param("estadoId") Long estado_id, Pageable pageable);
}