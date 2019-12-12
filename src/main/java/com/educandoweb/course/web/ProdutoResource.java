package com.educandoweb.course.web;

import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.repository.ProdutoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Produto}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    private static final String ENTITY_NAME = "produto";

    private final ProdutoRepository produtoRepository;

    public ProdutoResource(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * {@code POST  /produtos} : Create a new produto.
     *
     * @param produto the produto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produto, or with status {@code 400 (Bad Request)} if the produto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produtos")
    public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) throws Exception {
        log.debug("REST request to save Produto : {}", produto);
        if (produto.getId() != null) {
            throw new Exception("A new produto cannot already have an ID " + ENTITY_NAME + " idexists");
        }
        Produto result = produtoRepository.save(produto);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /produtos} : Updates an existing produto.
     *
     * @param produto the produto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produto,
     * or with status {@code 400 (Bad Request)} if the produto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produtos")
    public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) throws Exception {
        log.debug("REST request to update Produto : {}", produto);
        if (produto.getId() == null) {
            throw new Exception("Invalid id " + ENTITY_NAME + " idnull");
        }
        Produto result = produtoRepository.save(produto);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /produtos} : get all the produtos.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtos in body.
     */
    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getAllProdutos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Produtos");
        Page<Produto> page;
        if (eagerload) {
            page = produtoRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = produtoRepository.findAll(pageable);
        }

        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /produtos/:id} : get the "id" produto.
     *
     * @param id the id of the produto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        Optional<Produto> produto = produtoRepository.findOneWithEagerRelationships(id);
        return ResponseEntity.ok().body(produto.orElse(null));
    }

    /**
     * {@code DELETE  /produtos/:id} : delete the "id" produto.
     *
     * @param id the id of the produto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
