package com.educandoweb.course.web;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.security.SecurityUtils;
import com.educandoweb.course.service.ClienteService;
import com.educandoweb.course.service.MailService;
import com.educandoweb.course.service.UsuarioService;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.service.dto.PasswordChangeDTO;
import com.educandoweb.course.web.errors.AccountResourceException;
import com.educandoweb.course.web.errors.BadRequestAlertException;
import com.educandoweb.course.web.errors.EmailAlreadyUsedException;
import com.educandoweb.course.web.errors.EmailNotFoundException;
import com.educandoweb.course.web.errors.InvalidPasswordException;
import com.educandoweb.course.web.vm.KeyAndPasswordVM;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class ContaResource {

	private final Logger log = LoggerFactory.getLogger(ContaResource.class);
	
	private static final String ENTITY_NAME = "Conta";

	//@Value("${jhipster.clientApp.name}")
	//private String applicationName;

	private final UsuarioService userService;

	private final MailService mailService;

	private final ClienteService clienteService;

	public ContaResource(ClienteService clienteService, UsuarioService userService, MailService mailService) {
		this.clienteService = clienteService;
		this.userService = userService;
		this.mailService = mailService;
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
	 * {@code GET  /activate} : activate the registered user.
	 *
	 * @param key the activation key.
	 * @throws RuntimeException {@code 500 (Internal Server Error)} if the user
	 *                          couldn't be activated.
	 */
	@GetMapping("/activate")
	public void activateAccount(@RequestParam(value = "key") String key) {
		Optional<Usuario> user = userService.activateRegistration(key);
		if (!user.isPresent()) {
			throw new AccountResourceException("No user was found for this activation key");
		}
	}

	/**
	 * {@code GET  /authenticate} : check if the user is authenticated, and return
	 * its login.
	 *
	 * @param request the HTTP request.
	 * @return the login if the user is authenticated.
	 */
	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}
	
	/**
	 * {@code GET  /clientes/:id} : get the "id" cliente.
	 *
	 * @param id the id of the cliente to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the cliente, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/account")
	public ResponseEntity<ClienteDTO> getCliente() {		
		log.debug("REST request to get Cliente : {}");
		String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found")); 
		Optional<ClienteDTO> cliente = clienteService.findOne(userLogin);
		ClienteDTO result = cliente.orElseThrow(() -> new BadRequestAlertException("Invalid login", ENTITY_NAME, "login not found"));
		return ResponseEntity.ok().body(result);
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
	@PutMapping("/account")
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
	 * {@code POST  /account/change-password} : changes the current user's password.
	 *
	 * @param passwordChangeDto current and new password.
	 * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new
	 *                                  password is incorrect.
	 */
	@PostMapping(path = "/account/change-password")
	public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
		if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
	}

	/**
	 * {@code POST   /account/reset-password/init} : Send an email to reset the
	 * password of the user.
	 *
	 * @param mail the mail of the user.
	 * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address
	 *                                is not registered.
	 */
	@PostMapping(path = "/account/reset-password/init")
	public void requestPasswordReset(@RequestBody String mail) {
		Usuario user = userService.requestPasswordReset(mail).orElseThrow(EmailNotFoundException::new);
		//mailService.sendPasswordResetMail(user);
	}

	/**
	 * {@code POST   /account/reset-password/finish} : Finish to reset the password
	 * of the user.
	 *
	 * @param keyAndPassword the generated key and the new password.
	 * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is
	 *                                  incorrect.
	 * @throws RuntimeException         {@code 500 (Internal Server Error)} if the
	 *                                  password could not be reset.
	 */
	@PostMapping(path = "/account/reset-password/finish")
	public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
		if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
			throw new InvalidPasswordException();
		}
		Optional<Usuario> user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
		if (!user.isPresent()) {
			throw new AccountResourceException("No user was found for this reset key");
		}
	}
	
	/**
	 * Gets a list of all roles.
	 * 
	 * @return a string list of all roles.
	 */
	@GetMapping("/authorities")
	// @PreAuthorize("hasRole(\"" + ADMIN + "\")")
	public List<String> getAuthorities() {
		return userService.getAuthorities();
	}

	private static boolean checkPasswordLength(String password) {
		return !StringUtils.isEmpty(password) && password.length() >= ClienteNewDTO.PASSWORD_MIN_LENGTH
				&& password.length() <= ClienteNewDTO.PASSWORD_MAX_LENGTH;
	}
}
