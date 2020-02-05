package com.educandoweb.course.web;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.service.ProdutoService;
import com.educandoweb.course.service.dto.ProdutoDTO;
import com.educandoweb.course.web.errors.ObjectNotFoundAlertException;
import com.educandoweb.course.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Produto}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    private static final String ENTITY_NAME = "produto";

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }    

    /**
     * {@code GET  /produtos} : get all the produtos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtos in body.
     */
    @GetMapping("/produtos")
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutos(Pageable pageable) {
    	log.debug("REST request to get a page of Categorias");
        Page<ProdutoDTO> page = produtoService.findAll(pageable);        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);        
        return ResponseEntity.ok().headers(headers).body(page);
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
        Optional<Produto> produto = produtoService.findOne(id);
        Produto result = produto.orElseThrow(() -> new ObjectNotFoundAlertException("Invalid id", ENTITY_NAME, "id not found"));    	
    	return ResponseEntity.ok().body(result);    	
    }    
}
