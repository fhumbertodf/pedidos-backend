package com.educandoweb.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Estado;


/**
 * Spring Data  repository for the Estado entity.
 */

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
	Page<Estado> findAllByOrderByNome(Pageable pageable);

}
