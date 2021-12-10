package com.nttdata.breno.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.nttdata.breno.repository.ArCondicionadoRepository;

import com.nttdata.breno.model.ArCondicionado;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*; 
@WebMvcTest
public class ArCondicionadoControllerTest {

	
	@Autowired
	private ArCondicionadoController arCondicionadoController; 
	
	@MockBean
	private ArCondicionadoRepository arcondicionadorepository;
	
	@MockBean
	private ArCondicionado arCondicionado;
	
	@BeforeEach  
	public void setup() {
		
		standaloneSetup(this.arCondicionadoController,this.arcondicionadorepository,this.arCondicionado);  
 	}
		
	@Test
	public void deveRetornarNaoEncontrado_QuandoBuscarStatus() { 
		
		when(this.arcondicionadorepository.getById(10))
		.thenReturn(null);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/status/{id}", 10)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarStatus() { 
		
		ArCondicionado ac = new ArCondicionado();
				
		when(this.arcondicionadorepository.getById(1))
		.thenReturn(ac);
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/status/{id}",1)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarStatusAll() { 
				
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/statusall")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarCreated_QuandoSalvar() { 		
				
		given()
		.accept(ContentType.JSON)
		.when()
			.post("/save")
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
}
