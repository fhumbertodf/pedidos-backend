package com.educandoweb.course.web;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.security.SecurityUtils;
import com.educandoweb.course.service.ClienteService;
import com.educandoweb.course.service.UsuarioService;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.web.errors.AccountResourceException;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.EmailAlreadyUsedException;
import com.educandoweb.course.web.errors.InvalidPasswordException;
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
	 * {@code POST  /register} : register the user.
	 *
	 * @param managedUserVM the managed user View Model.
	 * @throws InvalidPasswordException  {@code 400 (Bad Request)} if the password
	 *                                   is incorrect.
	 * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is
	 *                                   already used.
	 * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is
	 *                                   already used.
	 */
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void createCliente(@Valid @RequestBody ClienteNewDTO cliente) {
		log.debug("REST request to save Cliente : {}", cliente);
		if (cliente.getId() != null) {
			throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
		} else {
			Usuario newUser = userService.registerUser(cliente);
			clienteService.insert(cliente, newUser);
			System.out.println(newUser.getActivationKey());
			// mailService.sendActivationEmail(newUser);
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
	@PutMapping("/clientes/account")
	public void updateCliente(@Valid @RequestBody ClienteDTO cliente) throws URISyntaxException {
		log.debug("REST request to update Cliente : {}", cliente);
		if (cliente.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		} else {
			clienteService.findOne(cliente.getId()).orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "id not found"));
		}
		Usuario user = userService.updateUser();        
		clienteService.save(cliente, user);
	}

	/**
	 * {@code GET  /clientes} : get all the clientes.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of clientes in body.
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
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
	@GetMapping("/clientes/account")
	public ResponseEntity<ClienteDTO> getCliente() {		
		log.debug("REST request to get Cliente : {}");
		String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found")); 
		Optional<ClienteDTO> cliente = clienteService.findOne(userLogin);
		ClienteDTO result = cliente.orElseThrow(() -> new BadRequestAlertException("Invalid login", ENTITY_NAME, "login not found"));
		return ResponseEntity.ok().body(result);
	}

	/**
	 * {@code DELETE  /clientes/:id} : delete the "id" cliente.
	 *
	 * @param id the id of the cliente to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/clientes/{email}")
	public ResponseEntity<Void> deleteCliente(@PathVariable String email) {
		log.debug("REST request to delete Cliente : {}", email);		
		userService.deleteUser(email);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, email)).build();
	}	
}
