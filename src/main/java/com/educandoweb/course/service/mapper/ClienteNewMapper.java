package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.domain.Cliente;
import com.educandoweb.course.domain.Endereco;
import com.educandoweb.course.domain.enumeration.TipoCliente;
import com.educandoweb.course.service.dto.ClienteDTO;
import com.educandoweb.course.service.dto.ClienteNewDTO;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Component
public class ClienteNewMapper implements EntityMapper<ClienteNewDTO, Cliente> {

	@Override
	public Cliente toEntity(ClienteNewDTO dto) {
		
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());
		cliente.setCpfOuCnpj(dto.getCpfOuCnpj());
		cliente.setTipoCliente(TipoCliente.toEnum(dto.getTipoCliente()));
				
		Cidade cidade = new Cidade();
		cidade.setId(dto.getCidadeId());
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setNumero(dto.getNumero());
		endereco.setComplemento(dto.getComplemento());
		endereco.setBairro(dto.getBairro());
		endereco.setCep(dto.getCep());
		endereco.setCidade(cidade);
		//endereco.setCliente(cliente);
				
		cliente.getEnderecos().add(endereco);
		
		cliente.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2()!=null) {
			cliente.getTelefones().add(dto.getTelefone2());
		}
		if (dto.getTelefone3()!=null) {
			cliente.getTelefones().add(dto.getTelefone3());
		}		
		return cliente;
	}

	@Override
	public ClienteNewDTO toDto(Cliente entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> toEntity(List<ClienteNewDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteNewDTO> toDto(List<Cliente> entityList) {
		// TODO Auto-generated method stub
		return null;
	}	
}