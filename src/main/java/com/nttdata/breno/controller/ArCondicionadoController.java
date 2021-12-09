package com.nttdata.breno.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nttdata.breno.model.ArCondicionado;
import com.nttdata.breno.repository.ArCondicionadoRepository;

@RestController
@RequestMapping
public class ArCondicionadoController {

	@Autowired
	private ArCondicionadoRepository arcondicionadorepository;
	
	
	@GetMapping(path = "/statusall")
	public List <ArCondicionado> status(){
		return arcondicionadorepository.findAll();				
	}
	
	@PostMapping(path = "/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ArCondicionado adicionar(@RequestBody ArCondicionado ac) {		
	return arcondicionadorepository.save(ac);
	}
	

	@GetMapping(path = "/status/{id}")
	public ArCondicionado status (@PathVariable("id") Integer id) {		
	return arcondicionadorepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Não Encontrado"));
	}
	
	@PutMapping(path = "/ligar/{id}")
	public void ligar (@PathVariable("id") Integer id) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setStatus("ligado");
		arcondicionadorepository.save(ac);		
		
	}
	
	@PutMapping(path = "/desligar/{id}")
	public void desligar (@PathVariable("id") Integer id) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setStatus("desligado");
		arcondicionadorepository.save(ac);
		
	}
	
	@PutMapping(path = "/mudartemp/{id}/{temp}")
	public void mudartemp (@PathVariable("id") Integer id,@PathVariable("temp") Integer temp) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setTemperatura(temp);
		arcondicionadorepository.save(ac);	
	}
	
	
}
