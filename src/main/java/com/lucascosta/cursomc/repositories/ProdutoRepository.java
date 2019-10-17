package com.lucascosta.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucascosta.cursomc.domain.Produto;

/*
 * implementada a interface repository que extende 'jpaRepository', que recebe a entidade a ser
 * persistida e o tipo de Id da mesma, com isso a entidade já recebe importantes métodos
 */

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	
}
