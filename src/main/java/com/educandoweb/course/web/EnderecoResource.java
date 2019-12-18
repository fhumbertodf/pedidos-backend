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

import com.educandoweb.course.domain.Endereco;
import com.educandoweb.course.repository.EnderecoRepository;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundException;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Endereco}.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    private final EnderecoRepository enderecoRepository;

    public EnderecoResource(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * {@code POST  /enderecos} : Create a new endereco.
     *
     * @param endereco the endereco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endereco, or with status {@code 400 (Bad Request)} if the endereco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enderecos")
    public ResponseEntity<Endereco> createEndereco(@Valid @RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", endereco);
        if (endereco.getId() != null) {
            throw new BadRequestAlertException(String.format("A new endereco cannot already have an ID %s idexists", ENTITY_NAME));
        }
        Endereco result = enderecoRepository.save(endereco);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId())).body(result);
    }

    /**
     * {@code PUT  /enderecos} : Updates an existing endereco.
     *
     * @param endereco the endereco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endereco,
     * or with status {@code 400 (Bad Request)} if the endereco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endereco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enderecos")
    public ResponseEntity<Endereco> updateEndereco(@Valid @RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", endereco);
        if (endereco.getId() == null) {
            throw new BadRequestAlertException(String.format("Invalid id %s idnull", ENTITY_NAME));
        }
        Endereco result = enderecoRepository.save(endereco);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /enderecos} : get all the enderecos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecos in body.
     */
    @GetMapping("/enderecos")
    public List<Endereco> getAllEnderecos() {
        log.debug("REST request to get all Enderecos");
        return enderecoRepository.findAll();
    }

    /**
     * {@code GET  /enderecos/:id} : get the "id" endereco.
     *
     * @param id the id of the endereco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endereco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enderecos/{id}")
    public ResponseEntity<Endereco> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        Endereco result = endereco.orElseThrow(() -> new ObjectNotFoundException(String.format("Invalid id %s id not found", ENTITY_NAME)));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code DELETE  /enderecos/:id} : delete the "id" endereco.
     *
     * @param id the id of the endereco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enderecos/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
