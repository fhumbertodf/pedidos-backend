package com.educandoweb.course.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.security.SecurityUtils;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.web.errors.AccountResourceException;
import com.educandoweb.course.web.errors.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();		
		String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
		Optional<Usuario> existingUser = userRepository.findOneByEmailIgnoreCase(dto.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
			list.add(new FieldMessage("email", "Email não pode ser encontrado"));
		}
		Optional<Usuario> user = userRepository.findOneByLogin(userLogin);
		if (!user.isPresent()) {
			list.add(new FieldMessage("login", "Login não pode ser encontrado"));
		}
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}