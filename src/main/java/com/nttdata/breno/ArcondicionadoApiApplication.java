package com.nttdata.breno;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//Classe Main principal do projeto

@SpringBootApplication
@EnableScheduling
public class ArcondicionadoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcondicionadoApiApplication.class, args);
			
	}	
	
}
