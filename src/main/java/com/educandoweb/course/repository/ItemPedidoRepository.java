package com.educandoweb.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.ItemPedido;


/**
 * Spring Data  repository for the ItemPedido entity.
 */
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
