package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.service.dto.ClienteDTO;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Component
public class ClienteMapper implements EntityMapper<ClienteDTO, Cliente> {

	@Override
	public Cliente toEntity(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setId(dto.getId());
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		return cliente;
	}

	@Override
	public ClienteDTO toDto(Cliente entity) {
		ClienteDTO cliente = new ClienteDTO(entity.getUser());
		cliente.setId(entity.getId());
		cliente.setNome(entity.getNome());
		cliente.setEmail(entity.getEmail());
		return cliente;
	}

	@Override
	public List<Cliente> toEntity(List<ClienteDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteDTO> toDto(List<Cliente> entityList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
