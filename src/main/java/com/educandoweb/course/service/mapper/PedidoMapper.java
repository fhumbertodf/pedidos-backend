package com.educandoweb.course.service.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.educandoweb.course.domain.Pedido;
import com.educandoweb.course.service.dto.PedidoDTO;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Component
public class PedidoMapper implements EntityMapper<PedidoDTO, Pedido> {

	@Override
	public Pedido toEntity(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setId(dto.getId());		
		return pedido;
	}

	@Override
	public PedidoDTO toDto(Pedido entity) {
		PedidoDTO pedido = new PedidoDTO();
		pedido.setId(entity.getId());		
		return pedido;
	}

	@Override
	public List<Pedido> toEntity(List<PedidoDTO> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PedidoDTO> toDto(List<Pedido> entityList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
