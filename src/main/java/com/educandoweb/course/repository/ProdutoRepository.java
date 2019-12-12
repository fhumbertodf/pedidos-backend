package com.educandoweb.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.domain.Produto;

/**
 * Spring Data repository for the Produto entity.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select distinct produto from Produto produto left join fetch produto.categorias",
        countQuery = "select count(distinct produto) from Produto produto")
    Page<Produto> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct produto from Produto produto left join fetch produto.categorias")
    List<Produto> findAllWithEagerRelationships();

    @Query("select produto from Produto produto left join fetch produto.categorias where produto.id =:id")
    Optional<Produto> findOneWithEagerRelationships(@Param("id") Long id);

}
