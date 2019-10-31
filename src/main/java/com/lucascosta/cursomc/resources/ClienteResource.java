package com.lucascosta.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucascosta.cursomc.domain.Cliente;
import com.lucascosta.cursomc.dto.ClienteDTO;
import com.lucascosta.cursomc.servicies.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	/*
	 * acessa o serviço 
	 */
	@Autowired
	private ClienteService service;
	
	/*
	 * e pede que ele busque a categoria por ID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	/*
	 * mêtodo que chama a inserção de clientes no BD
	 * retornando também o código HTTP de inserção de dados e também a URI 
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDTO){
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
    	service.delete(id);
    	return ResponseEntity.noContent().build();
    }

	/*
	 * para que ao fazer a requisição de todas as clientes não venha os produtos de cada categoria
	 * criasse um ClienteDTO(DATA TRANSFERENCE OBJECT) PARA QUE ELE SEJA PASSADO NA LISTA DE CATEGORIAS
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> obj = service.findAll();
		List<ClienteDTO> listDto = obj.stream().map(ob -> new ClienteDTO(ob)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0")Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction){
		Page<Cliente> obj = service.findPage(page,linesPerPage,orderBy,direction);
		Page<ClienteDTO> listDto = obj.map(ob -> new ClienteDTO(ob));
		return ResponseEntity.ok().body(listDto);
	}
}


