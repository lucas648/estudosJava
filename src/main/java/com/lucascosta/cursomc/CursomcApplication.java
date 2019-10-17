package com.lucascosta.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
//habilita que a classe execute alguma ação quando a classe iniciar
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lucascosta.cursomc.domain.Categoria;
import com.lucascosta.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	//é necessário uma instância do repository para se fazer a ligação com o BD
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	//é necessário que esse método seja criado e aqui é passada a ação a ser executada ao iniciar
	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		//através do repository passa uma lista de objetos, criada acima, para o BD
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
      		
	}

}
