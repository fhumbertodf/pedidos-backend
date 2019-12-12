package com.educandoweb.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Categoria;


/**
 * Spring Data  repository for the Categoria entity.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
