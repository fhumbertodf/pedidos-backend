package com.educandoweb.course.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.educandoweb.course.domain.enumeration.TipoCliente;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.service.dto.ClienteNewDTO;
import com.educandoweb.course.util.BR;
import com.educandoweb.course.web.errors.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO dto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (dto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(dto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (dto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(dto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		if (userRepository.findOneByLogin(dto.getLogin().toLowerCase()).isPresent()) {
			list.add(new FieldMessage("login", "Login já existente"));
		}
		if (userRepository.findOneByEmailIgnoreCase(dto.getEmail().toLowerCase()).isPresent()) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		if (!(!StringUtils.isEmpty(dto.getPassword()) && dto.getPassword().length() >= ClienteNewDTO.PASSWORD_MIN_LENGTH
				&& dto.getPassword().length() <= ClienteNewDTO.PASSWORD_MAX_LENGTH)) {
			list.add(new FieldMessage("password", "Password Inválido"));
		}
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
