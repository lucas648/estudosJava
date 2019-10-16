package com.lucascosta.cursomc.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	//instancia uma dependencia automaticamente 
	@Autowired
	private CategoriaRepository repo;
	
	//busca um objeto por ID e retorna ele como objeto 
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);  
		return obj.orElse(null); 
	}

}
