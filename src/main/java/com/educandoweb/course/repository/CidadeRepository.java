package com.educandoweb.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Cidade;


/**
 * Spring Data  repository for the Cidade entity.
 */

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
