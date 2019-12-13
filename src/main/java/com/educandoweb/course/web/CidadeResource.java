package com.educandoweb.course.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.repository.CidadeRepository;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundException;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Cidade}.
 */
@RestController
@RequestMapping("/api")
public class CidadeResource {

    private final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    private static final String ENTITY_NAME = "cidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CidadeRepository cidadeRepository;

    public CidadeResource(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * {@code POST  /cidades} : Create a new cidade.
     *
     * @param cidade the cidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cidade, or with status {@code 400 (Bad Request)} if the cidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cidades")
    public ResponseEntity<Cidade> createCidade(@Valid @RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidade);
        if (cidade.getId() != null) {
            throw new BadRequestAlertException(String.format("A new cidade cannot already have an ID %s idexists", ENTITY_NAME));
        }
        Cidade result = cidadeRepository.save(cidade);
        return ResponseEntity.created(new URI("/api/cidades/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /cidades} : Updates an existing cidade.
     *
     * @param cidade the cidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidade,
     * or with status {@code 400 (Bad Request)} if the cidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cidades")
    public ResponseEntity<Cidade> updateCidade(@Valid @RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}", cidade);
        if (cidade.getId() == null) {
            throw new BadRequestAlertException(String.format("Invalid id %s idnull", ENTITY_NAME));
        }
        Cidade result = cidadeRepository.save(cidade);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /cidades} : get all the cidades.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cidades in body.
     */
    @GetMapping("/cidades")
    public List<Cidade> getAllCidades() {
        log.debug("REST request to get all Cidades");
        return cidadeRepository.findAll();
    }

    /**
     * {@code GET  /cidades/:id} : get the "id" cidade.
     *
     * @param id the id of the cidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cidades/{id}")
    public ResponseEntity<Cidade> getCidade(@PathVariable Long id) {
        log.debug("REST request to get Cidade : {}", id);
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        Cidade result = cidade.orElseThrow(() -> new ObjectNotFoundException(String.format("Invalid id %s id not found", ENTITY_NAME)));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /cidades/:id} : delete the "id" cidade.
     *
     * @param id the id of the cidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cidades/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
