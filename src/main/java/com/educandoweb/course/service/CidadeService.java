package com.educandoweb.course.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.repository.CidadeRepository;
import com.educandoweb.course.service.dto.CidadeDTO;
import com.educandoweb.course.service.mapper.CidadeMapper;

/**
 * Service Implementation for managing {@link Cidade}.
 */
@Service
@Transactional
public class CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeService.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeService(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findAll(Long estadoId, Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findByEstado(estadoId, pageable)
            .map(cidadeMapper::toDto);
    }
}
