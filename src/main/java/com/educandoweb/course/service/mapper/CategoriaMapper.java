package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Categoria;
import com.educandoweb.course.service.dto.CategoriaDTO;

/**
 * Mapper for the entity {@link Categoria} and its DTO {@link CategoriaDTO}.
 */
@Component
public class CategoriaMapper implements EntityMapper<CategoriaDTO, Categoria> {

	@Override
	public Categoria toEntity(CategoriaDTO dto) {
		Categoria categoria = new Categoria();
		categoria.setId(dto.getId());
		categoria.setNome(dto.getNome());
		return categoria;
	}

	@Override
	public CategoriaDTO toDto(Categoria entity) {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setId(entity.getId());
		categoria.setNome(entity.getNome());
		return categoria;
	}

	@Override
	public List<Categoria> toEntity(List<CategoriaDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoriaDTO> toDto(List<Categoria> entityList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
