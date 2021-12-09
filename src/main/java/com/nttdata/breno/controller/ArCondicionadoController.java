package com.nttdata.breno.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;
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
import com.nttdata.breno.service.MqttService;
import com.nttdata.breno.service.Scheduler;
import com.nttdata.breno.service.TemperaturaAtual;

@RestController
@RequestMapping
@Component
@EnableScheduling
public class ArCondicionadoController {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
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
	public void mudarTemp (@PathVariable("id") Integer id,@PathVariable("temp") Integer temp) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setTemperatura(temp);
		arcondicionadorepository.save(ac);	
	}
	
	@PutMapping(path = "/programa/{id}/{horario}/{funcao}")	
	@Async
	public void mudarPrograma (@PathVariable("id") Integer id,@PathVariable("horario") String horario, @PathVariable("funcao") String funcao) throws InterruptedException {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setHorario(horario);
		ac.setPrograma(funcao);
		ac.setModoAuto(false);
		arcondicionadorepository.save(ac);	
		
		Scheduler scheduler = new Scheduler();
		scheduler.schedulerTask(horario, arcondicionadorepository, id, funcao);
	}
	
	@PutMapping(path = "/mudarmodo/{id}/{modo}")
	public void mudarModo (@PathVariable("id") Integer id,@PathVariable("modo") boolean modo) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setModoAuto(modo);
		arcondicionadorepository.save(ac);	
	}
	

	@Scheduled(cron = "* * * ? * *")
	public void mudarTempAuto() {
		
		TemperaturaAtual temp = new TemperaturaAtual();
		//System.out.println("A temperatura Atual em Varginha é " + temp.getTemperatura());		
		//System.out.println("arcondicionadorepository.count() = " + arcondicionadorepository.count());
		
		if (arcondicionadorepository.count() != 0){		
		
	    try {		
		List<ArCondicionado> acList = arcondicionadorepository.findAll();			
	
		for (int i = 1; i <= acList.size(); i++) {		
   //  	System.out.println("acList.get(i)= " + acList.get(i).isModoAuto()  );		
			
			if (acList.get(i).isModoAuto()==true){
				if (Float.parseFloat(temp.getTemperatura()) > 22 ) {
					acList.get(i).setStatus("ligado");
					System.out.println("ligado automaticamente");
				    MqttService client = new MqttService();
				    client.mqttConnection();
				    client.subscribeWith("my/test/topic");
				    client.publishWith("my/test/topic", "O Ar Condicionado foi Ligado Automaticamente");
				    
					
			   }else if(Float.parseFloat(temp.getTemperatura()) < 15 ) {
				   System.out.println("desligado automaticamente");
				   acList.get(i).setStatus("deligado");
			   }
			}
			
			arcondicionadorepository.save(acList.get(i));
		
			
		 }//end for
		
		}catch (Exception e) {
			
			}
	    
	  
		
	   }
	}
	
	
}
