package com.lucascosta.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
//habilita que a classe execute alguma ação quando a classe iniciar
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.domain.Cidade;
import com.lucascosta.cursomc.domain.Estado;
import com.lucascosta.cursomc.domain.Produto;
import com.lucascosta.cursomc.repositories.CategoriaRepository;
import com.lucascosta.cursomc.repositories.CidadeRepository;
import com.lucascosta.cursomc.repositories.EstadoRepository;
import com.lucascosta.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	/*
	 * é necessário uma instância do repository para se fazer a ligação com o BD
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	/*
	 * é necessário que esse método seja criado e aqui é passada a ação a ser executada ao iniciar
	 */
	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		/*
		 * faz a associação entre as categorias e seus respectivos produtos
		 */
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		/*
		 * faz a associação entre os produtos e suas respectivas categorias
		 */
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		/*
		 * através do repository passa uma lista de objetos, criada acima, para o BD
		 */
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
      	produtoRepository.saveAll(Arrays.asList(p1,p2,p3));	
      	
      	Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		/*
		 * como a relação de estado para cidade é de um para muitos, ou seja
		 * um estado pode ter várias cidades, os estados devem ser salvos primieros no BD
		 */
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2,cid3));
      		
	}

}
