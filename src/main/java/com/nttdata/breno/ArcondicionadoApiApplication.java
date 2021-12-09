package com.nttdata.breno;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nttdata.breno.controller.ArCondicionadoController;
import com.nttdata.breno.model.ArCondicionado;
import com.nttdata.breno.model.TemperaturaAtual;
import com.nttdata.breno.repository.ArCondicionadoRepository;

@SpringBootApplication
public class ArcondicionadoApiApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(ArcondicionadoApiApplication.class, args);
		
	    
		
		TemperaturaAtual temp = new TemperaturaAtual();
		System.out.println("A temperatura Atual em Varginha é " + temp.getTemperatura());
		
		if (Float.parseFloat(temp.getTemperatura()) > 22 ) {
			System.out.println("Temperatura maior que 22, o ar está ligado");
			System.out.println("Avisando usuários...");
		
		
	   }else if(Float.parseFloat(temp.getTemperatura()) < 15 ) {
		   System.out.println("Temperatura menor que 15, o ar está desligado");
	   }
	
	
	
	}	
	
}
