package com.educandoweb.course.web;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.service.UsuarioService;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.service.dto.PasswordChangeDTO;
import com.educandoweb.course.web.errors.AccountResourceException;
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
	
	private final UsuarioService userService;

	public ContaResource(UsuarioService userService) {
		this.userService = userService;
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
		//Usuario user = 
		userService.requestPasswordReset(mail).orElseThrow(EmailNotFoundException::new);
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
	
	private static boolean checkPasswordLength(String password) {
		return !StringUtils.isEmpty(password) && password.length() >= ClienteNewDTO.PASSWORD_MIN_LENGTH
				&& password.length() <= ClienteNewDTO.PASSWORD_MAX_LENGTH;
	}
}
