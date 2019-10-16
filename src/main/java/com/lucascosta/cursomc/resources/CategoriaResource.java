package com.lucascosta.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucascosta.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		
		//instancia dois objetos 'Categoria'
		Categoria cat1 = new Categoria(1, "informática");
		Categoria cat2 = new Categoria(2, "Escritório");		
		
		//cria uma lista de 'Categoria' como um ArrayList
		List<Categoria> lista = new ArrayList<>();
		//adiciona as duas categorias criadas ao array de categorias
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}

}
