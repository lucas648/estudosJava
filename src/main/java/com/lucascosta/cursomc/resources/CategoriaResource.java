package com.lucascosta.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.servicies.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	//acessa o serviço 
	@Autowired
	private CategoriaService service;
	
	//e pede que ele busque a categoria por ID
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
	
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
		/*instancia dois objetos 'Categoria'
		Categoria cat1 = new Categoria(1, "informática");
		Categoria cat2 = new Categoria(2, "Escritório");*/		
		
		/*cria uma lista de 'Categoria' como um ArrayList
		List<Categoria> lista = new ArrayList<>();
		//adiciona as duas categorias criadas ao array de categorias
		lista.add(cat1);
		lista.add(cat2);*/
		
	}

}
