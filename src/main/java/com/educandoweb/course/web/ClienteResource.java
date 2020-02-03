package com.educandoweb.course.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.service.ClienteService;
import com.educandoweb.course.service.UsuarioService;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.web.util.HeaderUtil;
import com.educandoweb.course.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.educandoweb.course.domain.Cliente}.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

	private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

	private static final String ENTITY_NAME = "cliente";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final ClienteService clienteService;

	private final UsuarioService userService;

	public ClienteResource(ClienteRepository clienteRepository, ClienteService clienteService,
			UsuarioRepository userRepository, UsuarioService userService) {
		this.clienteService = clienteService;
		this.userService = userService;		
	}	

	/**
	 * {@code GET  /clientes} : get all the clientes.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of clientes in body.
	 */
	@GetMapping("/clientes")
	public ResponseEntity<Page<ClienteDTO>> getAllClientes(Pageable pageable) {
		log.debug("REST request to get all Clientes");
		Page<ClienteDTO> page = clienteService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page);
	}	

	/**
	 * {@code DELETE  /clientes/:id} : delete the "id" cliente.
	 *
	 * @param id the id of the cliente to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/clientes/{email}")
	public ResponseEntity<Void> deleteCliente(@PathVariable String email) {
		log.debug("REST request to delete Cliente : {}", email);		
		userService.deleteUser(email);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, email)).build();
	}	
}
