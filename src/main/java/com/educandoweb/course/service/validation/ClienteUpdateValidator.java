package com.educandoweb.course.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.User;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.UserRepository;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.web.errors.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	//@Autowired
	//private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO dto, ConstraintValidatorContext context) {
		
		//@SuppressWarnings("unchecked")
		//Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		//Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Optional<Cliente> existingCliente = clienteRepository.findOneByEmailIgnoreCase(dto.getEmail());
        if (existingCliente.isPresent() && (!existingCliente.get().getId().equals(dto.getId()))) {
            list.add(new FieldMessage("email", "Email já usado"));
        }
        Optional<User> existingUser = userRepository.findOneByLogin(dto.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(dto.getId()))) {
            list.add(new FieldMessage("login", "Login já usado"));
        }

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}