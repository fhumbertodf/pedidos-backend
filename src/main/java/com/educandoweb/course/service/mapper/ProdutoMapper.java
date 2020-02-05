package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Produto;
import com.educandoweb.course.service.dto.ProdutoDTO;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Component
public class ProdutoMapper implements EntityMapper<ProdutoDTO, Produto> {

	@Override
	public Produto toEntity(ProdutoDTO dto) {
		Produto produto = new Produto();
		produto.setId(dto.getId());		
		return produto;
	}

	@Override
	public ProdutoDTO toDto(Produto entity) {
		ProdutoDTO produto = new ProdutoDTO();
		produto.setId(entity.getId());		
		return produto;
	}

	@Override
	public List<Produto> toEntity(List<ProdutoDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdutoDTO> toDto(List<Produto> entityList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
