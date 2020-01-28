package com.educandoweb.course.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.User;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.UserRepository;
import com.educandoweb.course.service.ClienteService;
import com.educandoweb.course.service.MailService;
import com.educandoweb.course.service.UserService;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.ObjectNotFoundAlertException;
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

	private final UserService userService;

	private final MailService mailService;

	public ClienteResource(ClienteRepository clienteRepository, ClienteService clienteService,
			UserRepository userRepository, UserService userService, MailService mailService) {
		this.clienteService = clienteService;
		this.userService = userService;
		this.mailService = mailService;
	}

	/**
	 * {@code POST  /clientes} : Create a new cliente.
	 *
	 * @param cliente the cliente to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new cliente, or with status {@code 400 (Bad Request)} if the
	 *         cliente has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/clientes")
	public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteNewDTO cliente) {
		log.debug("REST request to save Cliente : {}", cliente);
		if (cliente.getId() != null) {
			throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");			
		} else {
			User newUser = userService.createUser(cliente);
			ClienteDTO result = clienteService.insert(cliente, newUser);
			mailService.sendCreationEmail(newUser);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
					.toUri();
			return ResponseEntity.created(uri).headers(
					HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
					.body(result);
		}
	}

	/**
	 * {@code PUT  /clientes} : Updates an existing cliente.
	 *
	 * @param cliente the cliente to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated cliente, or with status {@code 400 (Bad Request)} if the
	 *         cliente is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the cliente couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/clientes")
	public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO cliente) throws URISyntaxException {
		log.debug("REST request to update Cliente : {}", cliente);		
        Optional<User> updatedUser = userService.updateUser(cliente);
        updatedUser.orElseThrow(() -> new ObjectNotFoundAlertException("userManagement.updated", "User", cliente.getLogin()));
		if (cliente.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		} else {
			clienteService.findOne(cliente.getId())
					.orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "id not found"));
		}
		ClienteDTO result = clienteService.save(cliente, updatedUser.get());
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cliente.getId().toString()))
				.body(result);
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
	 * {@code GET  /clientes/:id} : get the "id" cliente.
	 *
	 * @param id the id of the cliente to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the cliente, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
		log.debug("REST request to get Cliente : {}", id);
		Optional<Cliente> cliente = clienteService.findOne(id);
		Cliente result = cliente
				.orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "id not found"));
		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code DELETE  /clientes/:id} : delete the "id" cliente.
	 *
	 * @param id the id of the cliente to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
		log.debug("REST request to delete Cliente : {}", id);
		clienteService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
