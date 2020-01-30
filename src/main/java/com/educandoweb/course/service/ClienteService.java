package com.educandoweb.course.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.User;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.service.mapper.ClienteMapper;
import com.educandoweb.course.service.mapper.ClienteNewMapper;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteService {

	private final Logger log = LoggerFactory.getLogger(ClienteService.class);

	private final ClienteRepository clienteRepository;

	private final ClienteMapper clienteMapper;

	private final ClienteNewMapper clienteNewMapper;

	public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper,
			ClienteNewMapper clienteNewMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteMapper = clienteMapper;
		this.clienteNewMapper = clienteNewMapper;		
	}

	/**
	 * Save a cliente.
	 *
	 * @param clienteDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ClienteDTO insert(ClienteNewDTO clienteDTO, User user) {
		log.debug("Request to save Cliente : {}", clienteDTO);
		Cliente cliente = clienteNewMapper.toEntity(clienteDTO);
		cliente.setUser(user);
		user.setCliente(cliente);				
		return clienteMapper.toDto(clienteRepository.save(cliente));
	}

	/**
	 * Save a cliente.
	 *
	 * @param clienteDTO the entity to save.
	 * @return the persisted entity.
	 */
	public ClienteDTO save(ClienteDTO clienteDTO, User user) {
		log.debug("Request to save Cliente : {}", clienteDTO);
		Cliente cliente = clienteMapper.toEntity(clienteDTO);
		cliente.setUser(user);
		cliente = clienteRepository.save(cliente);
		return clienteMapper.toDto(cliente);
	}

	/**
	 * Get all the clientes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Clientes");
		return clienteRepository.findAll(pageable).map(clienteMapper::toDto);
	}

	/**
	 * Get one cliente by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<Cliente> findOne(Long id) {
		log.debug("Request to get Cliente : {}", id);
		return clienteRepository.findById(id); // .map(clienteMapper::toDto);
	}

	/**
	 * Delete the cliente by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Cliente : {}", id);
		clienteRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
    public Optional<ClienteDTO> getClienteWithAuthoritiesByLogin(String login) {
        return clienteRepository.findOneByEmailIgnoreCase(login).map(clienteMapper::toDto);
    }
}
