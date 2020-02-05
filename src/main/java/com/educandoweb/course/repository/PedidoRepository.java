package com.educandoweb.course.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Pedido;


/**
 * Spring Data  repository for the Pedido entity.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Transactional(readOnly=true)
	@Query("select p from Pedido where p.cliente.email : email")
	Page<Pedido> findByCliente(@Param("email") String email, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	@Query("select p from Pedido where p.cliente.email : email and p.id = :id")
	Optional<Pedido> findByCliente(@Param("email") String email, @Param("id") Long id);
}
