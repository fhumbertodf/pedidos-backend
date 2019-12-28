package com.educandoweb.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Pagamento;


/**
 * Spring Data  repository for the Pagamento entity.
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

}
