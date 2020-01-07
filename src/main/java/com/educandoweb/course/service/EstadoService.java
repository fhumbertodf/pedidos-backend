package com.educandoweb.course.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Estado;
import com.educandoweb.course.repository.EstadoRepository;
import com.educandoweb.course.service.dto.EstadoDTO;
import com.educandoweb.course.service.mapper.EstadoMapper;

/**
 * Service Implementation for managing {@link Estado}.
 */
@Service
@Transactional
public class EstadoService {

    private final Logger log = LoggerFactory.getLogger(EstadoService.class);

    private final EstadoRepository estadoRepository;

    private final EstadoMapper estadoMapper;

    public EstadoService(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    /**
     * Get all the estados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Estados");
        return estadoRepository.findAllByOrderByNome(pageable).map(estadoMapper::toDto);
    }    
}
