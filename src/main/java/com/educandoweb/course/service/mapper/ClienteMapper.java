package com.educandoweb.course.service.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.Endereco;
import com.educandoweb.course.domain.enumeration.TipoCliente;
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
		cliente.setCpfOuCnpj(dto.getCpfOuCnpj());		
		Endereco endereco = new Endereco();		
		endereco.setBairro(dto.getBairro());
		endereco.setCep(dto.getCep());
		endereco.setComplemento(dto.getComplemento());		
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setNumero(dto.getNumero());
		Cidade cidade = new Cidade();
		cidade.setId(dto.getCidadeId());
		endereco.setCidade(cidade);
		cliente.setEnderecos(new HashSet<Endereco>(Arrays.asList(endereco)));
		cliente.setTelefones(new HashSet<String>(Arrays.asList(dto.getTelefone1(), dto.getTelefone2(), dto.getTelefone3())));
		cliente.setTipoCliente(TipoCliente.toEnum(dto.getTipoCliente()));
		return cliente;
	}

	@Override
	public ClienteDTO toDto(Cliente entity) {
		ClienteDTO cliente = new ClienteDTO(entity.getUser());
		cliente.setNome(entity.getNome());
		cliente.setEmail(entity.getEmail());		
		Endereco endereco = entity.getEnderecos().stream().findFirst().get();		
		cliente.setBairro(endereco.getBairro());
		cliente.setCep(endereco.getCep());
		cliente.setCidadeId(endereco.getCidade().getId());
		cliente.setComplemento(endereco.getComplemento());
		cliente.setCpfOuCnpj(entity.getCpfOuCnpj());
		cliente.setLogradouro(endereco.getLogradouro());
		cliente.setNumero(endereco.getNumero());		
		Object[] telefones = entity.getTelefones().stream().toArray();		
		cliente.setTelefone1(telefones.length >= 1 ? telefones[0].toString() : "");
		cliente.setTelefone2(telefones.length >= 2 ? telefones[1].toString() : "");
		cliente.setTelefone3(telefones.length >= 3 ? telefones[2].toString() : "");
		cliente.setTipoCliente(entity.getTipoCliente().getCod());				
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
