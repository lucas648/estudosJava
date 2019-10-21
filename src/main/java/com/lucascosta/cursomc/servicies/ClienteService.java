package com.lucascosta.cursomc.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Cliente;
import com.lucascosta.cursomc.repositories.ClienteRepository;
import com.lucascosta.cursomc.servicies.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	/*
	 * instancia uma dependencia automaticamente 
	 */
	@Autowired
	private ClienteRepository repo;
	
	/*
	 * busca um objeto por ID e retorna ele como objeto 
	 * se o ID passado não for encontrado, roda uma exeção com a menssagem passada 
	 */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
