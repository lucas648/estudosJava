package com.lucascosta.cursomc.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.dto.CategoriaDTO;
import com.lucascosta.cursomc.repositories.CategoriaRepository;
import com.lucascosta.cursomc.servicies.exceptions.DataIntegrityException;
import com.lucascosta.cursomc.servicies.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	/*
	 * instancia uma dependencia automaticamente 
	 */
	@Autowired
	private CategoriaRepository repo;
	
	/*
	 * busca um objeto por ID e retorna ele como objeto 
	 * se o ID passado não for encontrado, roda uma exeção com a menssagem passada 
	 */
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	/*
	 * método para a inserção de Categorias no BD
	 */
	public Categoria insert(Categoria obj) {
		Categoria newOBJ = find(obj.getId());
		updateData(newOBJ,obj);
		return repo.save(newOBJ);
				
	}
	
	/*
	 * mêtodo para atualizar uma Categoria no BD, que verifica se o ID passado é igual ao 
	 * que está inserido no BD
	 */
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		  repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma Categoria que tenha produtos relacionados a ela!!");
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	/*
	 * ultilizando do método Page do Spring é feita a paginação de categorias
	 */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/*
	 * transforma uma categoriaDTO em uma categoria
	 */
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria newOBJ, Categoria obj) {
		newOBJ.setNome(obj.getNome());
	}
}
