package com.lucascosta.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucascosta.cursomc.domain.Pedido;
import com.lucascosta.cursomc.servicies.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	/*
	 * acessa o serviço 
	 */
	@Autowired
	private PedidoService service;
	
	/*
	 * e pede que ele busque a categoria por ID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

}
