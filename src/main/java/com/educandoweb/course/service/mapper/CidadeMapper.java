package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Cidade;
import com.educandoweb.course.service.dto.CidadeDTO;

@Component
public class CidadeMapper implements EntityMapper<CidadeDTO, Cidade> {

	@Override
	public Cidade toEntity(CidadeDTO dto) {
		Cidade cidade = new Cidade();
		cidade.setId(dto.getId());
		cidade.setNome(dto.getNome());			
		return cidade;
	}

	@Override
	public CidadeDTO toDto(Cidade entity) {
		CidadeDTO cidade = new CidadeDTO();
		cidade.setId(entity.getId());
		cidade.setNome(entity.getNome());			
		return cidade;
	}

	@Override
	public List<Cidade> toEntity(List<CidadeDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CidadeDTO> toDto(List<Cidade> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

}
