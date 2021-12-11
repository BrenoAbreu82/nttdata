package com.nttdata.breno.controller;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import com.nttdata.breno.repository.ArCondicionadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.breno.model.ArCondicionado;
import io.restassured.http.ContentType;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*; 

//Classe para Testar a Controller
@WebMvcTest
public class ArCondicionadoControllerTest {

	
	@Autowired
	private ArCondicionadoController arCondicionadoController; 
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ArCondicionadoRepository arcondicionadorepository;
	
	@MockBean
	private ArCondicionado arCondicionado;
	
	@BeforeEach
	public void setup() {
        
		arCondicionado = new ArCondicionado(1,23,"nulo","nulo","desligado",false);
       
	    arCondicionadoController.getArcondicionadorepository().save(arCondicionado);
		
		when(arcondicionadorepository.getById(arCondicionado.getId()))
		.thenReturn(arCondicionado);
      
	   standaloneSetup(this.arCondicionadoController);  
 	}
		
	
	@Test
	public void deveRetornarSucesso_QuandoBuscarStatus() {		
	
		given()
			.contentType(ContentType.JSON)
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
	public void deveRetornarBadRequest_QuandoSalvarSemArgumentos() { 		
				
		given()
		.accept(ContentType.JSON)
		.when()
			.post("/save")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarCreated_QuandoSalvar() throws Exception { 		
						
		String valueStr = objectMapper.writeValueAsString(arCondicionado);	
		
		given()
		.contentType(ContentType.JSON)
		.body(valueStr)
		.when()
			.post("/save")
		.then()
		.status(HttpStatus.CREATED);		
	}
	
	
	@Test
	public void deveRetornarOK_QuandoligarAC(){
			   	 
		given()
		.contentType(ContentType.JSON)
		.when()
			.put("/ligar/{id}",1)
		.then().statusCode(HttpStatus.OK.value());
			
	}
	
	@Test
	public void deveRetornarOK_QuandoDesligarAC(){
			   	 
		given()
		.contentType(ContentType.JSON)
		.when()
			.put("/desligar/{id}",1)
		.then().statusCode(HttpStatus.OK.value());
			
	}
	
	@Test
	public void deveRetornarOK_QuandoMudarTemperatura(){
			   	 
		given()
		.contentType(ContentType.JSON)
		.when()
			.put("/mudartemp/{id}/{temp}",1,20)
		.then().statusCode(HttpStatus.OK.value());
			
	}
	
	@Test
	public void deveRetornarOK_QuandoMudarModo(){
			   	 
		given()
		.contentType(ContentType.JSON)
		.when()
			.put("/mudarmodo/{id}/{modo}",1,true)
		.then().statusCode(HttpStatus.OK.value());
			
	}	
	
	//Testes para verificar se o setup está ok
	
	@Test
	public void testeArCondicionadoTemperatura() {
		
		 Assertions.assertEquals(23, arCondicionado.getTemperatura());		
	}
	
	@Test
	public void testeArCondicionadoId() {
		
		 Assertions.assertEquals(1, arCondicionado.getId());
		
	}
	
	@Test
	public void testeRepository() {
		
		Integer temp = arcondicionadorepository.getById(1).getTemperatura();
		
		Assertions.assertEquals(23, temp);
		
	}
	

	
}
