package com.lucascosta.cursomc.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Pedido;
import com.lucascosta.cursomc.repositories.PedidoRepository;
import com.lucascosta.cursomc.servicies.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	/*
	 * instancia uma dependencia automaticamente 
	 */
	@Autowired
	private PedidoRepository repo;
	
	/*
	 * busca um objeto por ID e retorna ele como objeto 
	 * se o ID passado não for encontrado, roda uma exeção com a menssagem passada 
	 */
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
