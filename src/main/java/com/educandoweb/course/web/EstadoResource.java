package com.educandoweb.course.web;

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

import com.educandoweb.course.service.CidadeService;
import com.educandoweb.course.service.EstadoService;
import com.educandoweb.course.service.dto.CidadeDTO;
import com.educandoweb.course.service.dto.EstadoDTO;
import com.educandoweb.course.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Estado}.
 */
@RestController
@RequestMapping("/api")
public class EstadoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoResource.class);

    //private static final String ENTITY_NAME = "estado";

    private final EstadoService estadoService;
    
    private final CidadeService cidadeService;

    public EstadoResource(EstadoService estadoService, CidadeService cidadeService) {
        this.estadoService = estadoService;
        this.cidadeService = cidadeService;
    }

   /**
     * {@code GET  /estados} : get all the estados.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estados in body.
     */
    @GetMapping("/estados")
    public ResponseEntity<Page<EstadoDTO>> getAllEstados(Pageable pageable) {
        log.debug("REST request to get all Estados");
        Page<EstadoDTO> page = estadoService.findAll(pageable);        
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);        
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * {@code GET  /estados/:id} : get the "id" estado.
     *
     * @param id the id of the estado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estados/{estadoId}/cidades")
    public ResponseEntity<Page<CidadeDTO>> getAllCidades(@PathVariable Long estadoId, Pageable pageable) {
        log.debug("REST request to get all Cidade: {}", estadoId);
        Page<CidadeDTO> page = cidadeService.findAll(estadoId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }   
}
