package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Estado;
import com.educandoweb.course.service.dto.EstadoDTO;

@Component
public class EstadoMapper implements EntityMapper<EstadoDTO, Estado> {

	@Override
	public Estado toEntity(EstadoDTO dto) {
		Estado estado = new Estado();
		estado.setId(dto.getId());
		estado.setNome(dto.getNome());
		return estado;
	}

	@Override
	public EstadoDTO toDto(Estado entity) {
		EstadoDTO estado = new EstadoDTO();
		estado.setId(entity.getId());
		estado.setNome(entity.getNome());
		return estado;
	}

	@Override
	public List<Estado> toEntity(List<EstadoDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EstadoDTO> toDto(List<Estado> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

}
