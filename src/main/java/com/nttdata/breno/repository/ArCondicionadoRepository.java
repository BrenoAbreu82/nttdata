package com.nttdata.breno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.breno.model.ArCondicionado;

//Repositório para Conexão com a Base de dados H2
@Repository
public interface ArCondicionadoRepository extends JpaRepository<ArCondicionado, Integer>{

	
	
}
