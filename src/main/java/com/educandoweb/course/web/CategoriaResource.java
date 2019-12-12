package com.educandoweb.course.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.domain.Categoria;
import com.educandoweb.course.repository.CategoriaRepository;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundException;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Categoria}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    private static final String ENTITY_NAME = "categoria";

    private final CategoriaRepository categoriaRepository;

    public CategoriaResource(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * {@code POST  /categorias} : Create a new categoria.
     *
     * @param categoria the categoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoria, or with status {@code 400 (Bad Request)} if the categoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorias")
    public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoria);
        if (categoria.getId() != null) {
            throw new BadRequestAlertException(String.format("A new produto cannot already have an ID %s idexists", ENTITY_NAME));
        }
        Categoria result = categoriaRepository.save(categoria);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /categorias} : Updates an existing categoria.
     *
     * @param categoria the categoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoria,
     * or with status {@code 400 (Bad Request)} if the categoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorias")
    public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
        log.debug("REST request to update Categoria : {}", categoria);
        if (categoria.getId() == null) {
            throw new BadRequestAlertException(String.format("Invalid id %s idnull", ENTITY_NAME));
        }
        Categoria result = categoriaRepository.save(categoria);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /categorias} : get all the categorias.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorias in body.
     */
    @GetMapping("/categorias")
    public List<Categoria> getAllCategorias() {
        log.debug("REST request to get all Categorias");
        return categoriaRepository.findAll();
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categoria.
     *
     * @param id the id of the categoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        Categoria result = categoria.orElseThrow(() -> new ObjectNotFoundException(String.format("Object not found %s", ENTITY_NAME)));    	
    	return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /categorias/:id} : delete the "id" categoria.
     *
     * @param id the id of the categoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
