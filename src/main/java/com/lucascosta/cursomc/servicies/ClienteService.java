package com.lucascosta.cursomc.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Cliente;
import com.lucascosta.cursomc.dto.ClienteDTO;
import com.lucascosta.cursomc.repositories.ClienteRepository;
import com.lucascosta.cursomc.servicies.exceptions.DataIntegrityException;
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


	/*
	 * mêtodo para atualizar uma Cliente no BD, que verifica se o ID passado é igual ao 
	 * que está inserido no BD
	 */
	public Cliente update(Cliente obj) {
		Cliente newOBJ = find(obj.getId());
		updateData(newOBJ,obj);
		return repo.save(newOBJ);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		  repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma Cliente que tenha pedidos relacionados a ele!!");
		}
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	/*
	 * ultilizando do método Page do Spring é feita a paginação de clientes
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/*
	 * transforma uma clienteDTO em uma cliente
	 */
	public Cliente fromDTO(ClienteDTO objDTO) {
	 return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}
	
	private void updateData(Cliente newOBJ, Cliente obj) {
		newOBJ.setNome(obj.getNome());
		newOBJ.setEmail(obj.getEmail());
	}
}
