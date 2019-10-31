package com.lucascosta.cursomc.servicies;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucascosta.cursomc.domain.Cidade;
import com.lucascosta.cursomc.domain.Cliente;
import com.lucascosta.cursomc.domain.Endereco;
import com.lucascosta.cursomc.domain.enums.TipoCliente;
import com.lucascosta.cursomc.dto.ClienteDTO;
import com.lucascosta.cursomc.dto.ClienteNewDto;
import com.lucascosta.cursomc.repositories.ClienteRepository;
import com.lucascosta.cursomc.repositories.EnderecoRepository;
import com.lucascosta.cursomc.servicies.exceptions.DataIntegrityException;
import com.lucascosta.cursomc.servicies.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	/*
	 * instancia uma dependencia automaticamente 
	 */
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository endereco;
	
	/*
	 * busca um objeto por ID e retorna ele como objeto 
	 * se o ID passado não for encontrado, roda uma exeção com a menssagem passada 
	 */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}


	@Transactional
	public Cliente insert(Cliente obj) {
      obj.setId(null);
      obj = repo.save(obj);
      endereco.saveAll(obj.getEnderecos());
	  return obj;
				
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
	
	public Cliente fromDTO(ClienteNewDto objDto) {
		Cliente cli =new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newOBJ, Cliente obj) {
		newOBJ.setNome(obj.getNome());
		newOBJ.setEmail(obj.getEmail());
	}
}
