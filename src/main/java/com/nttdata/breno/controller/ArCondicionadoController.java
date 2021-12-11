package com.nttdata.breno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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

//Classe Controller do Projeto

@RestController
@RequestMapping
@Component
@EnableScheduling
public class ArCondicionadoController {

	@Autowired
	private ArCondicionadoRepository arcondicionadorepository;
	
	//Retorna todos os Equipamentos na base.
	@GetMapping(path = "/statusall")
	public List <ArCondicionado> status(){
		return arcondicionadorepository.findAll();				
	}
	
	//Inserção de um novo Ar Condicionado
	@PostMapping(path = "/save")
	@ResponseStatus(HttpStatus.CREATED)
	public ArCondicionado adicionar(@RequestBody ArCondicionado ac) {		
	return arcondicionadorepository.save(ac);
	}
	
	//Exige todos os dados de um Ar Condicionado por Id
	@GetMapping(path = "/status/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ArCondicionado status (@PathVariable("id") Integer id) {		
	
	return arcondicionadorepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Não Encontrado"));
	 
	}
	
	//ligando o Ar Condicionado manualmente. 
	@PutMapping(path = "/ligar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String ligar (@PathVariable("id") Integer id) {			
		
		ArCondicionado ac = arcondicionadorepository.findById(id)
				            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Não Encontrado"));
		if (ac!=null) {		
			ac.setStatus("ligado");
			arcondicionadorepository.save(ac);
			return ("Operação Realizada com Sucesso. O Status atual é:"+ac.getStatus());
			}
		
		else return HttpStatus.NOT_FOUND.toString();
	}
	
	public ArCondicionadoRepository getArcondicionadorepository() {
		return arcondicionadorepository;
	}

	public void setArcondicionadorepository(ArCondicionadoRepository arcondicionadorepository) {
		this.arcondicionadorepository = arcondicionadorepository;
	}
	
	//desligando o Ar Condicionado manualmente.
	@PutMapping(path = "/desligar/{id}")
	public String desligar (@PathVariable("id") Integer id) {			
		ArCondicionado ac = arcondicionadorepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Não Encontrado"));
		if (ac!=null) {		
			ac.setStatus("desligado");
			arcondicionadorepository.save(ac);
			return ("Operação Realizada com Sucesso. O Status atual é:"+ac.getStatus());
		}
		
		else return HttpStatus.NOT_FOUND.toString();
	}
	
	//Alterando a temperatura manualmente
	@PutMapping(path = "/mudartemp/{id}/{temp}")
	public String mudarTemp (@PathVariable("id") Integer id,@PathVariable("temp") Integer temp) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setTemperatura(temp);
		arcondicionadorepository.save(ac);	
		return ("Operação Realizada com Sucesso. A Temperatura atual é:"+ac.getTemperatura());
	}
	
	//Fazer o agendamento. Deve-se fornecer o ID, horário em formato HH:mm e a Função que pode ser ligar ou desligar. 
	@PutMapping(path = "/programa/{id}/{horario}/{funcao}")	
	@Async
	public void mudarPrograma (@PathVariable("id") Integer id,@PathVariable("horario") String horario, @PathVariable("funcao") String funcao) throws InterruptedException {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setHorario(horario);
		ac.setPrograma(funcao);
		ac.setModoAuto(false);
		arcondicionadorepository.save(ac);	
		
		//Método que faz o agendamento
		Scheduler scheduler = new Scheduler();
		scheduler.schedulerTask(horario, arcondicionadorepository, id, funcao);		
		
	}
	
	//Altera o modo de automático(true) ou manual(false). No modo automático o Ar Condicionado considera ligar/desligar pela temperatura local
	//No modo automático o Ar Condicionado considera ligar/desligar pela temperatura local.
	@PutMapping(path = "/mudarmodo/{id}/{modo}")
	public void mudarModo (@PathVariable("id") Integer id,@PathVariable("modo") boolean modo) {			
		ArCondicionado ac = arcondicionadorepository.findById(id).get();
		ac.setModoAuto(modo);
		arcondicionadorepository.save(ac);	
	}
	
	//Método para ligar e delisgar automáticamente de acordo com a temperatura local
	@Scheduled(cron = "* * * ? * *")
	public void mudarTempAuto() {
		
		//Classe que obtem a Temperatura corrente. 
		TemperaturaAtual temp = new TemperaturaAtual();
		if (arcondicionadorepository.count() != 0){		
		
	    try {		
		List<ArCondicionado> acList = arcondicionadorepository.findAll();			
	
	
		for (int i = 0; i <= acList.size(); i++) {				
			
			if (acList.get(i).isModoAuto()){
				if (Float.parseFloat(temp.getTemperatura()) > 22 ) {
					acList.get(i).setStatus("ligado");
					System.out.println("ligado automaticamente. Temperatura Atual="+temp.getTemperatura());
				    
					//Classe responsável por tratar o protocolo e conexão MQTT
					MqttService client = new MqttService();
				    client.mqttConnection();
				    client.subscribeWith("my/test/topic");
				    client.publishWith("my/test/topic", "O Ar Condicionado foi Ligado Automaticamente");
				    			
			   }else if(Float.parseFloat(temp.getTemperatura()) < 15 ) {
				   System.out.println("desligado automaticamente. Temperatura Atual="+temp.getTemperatura());
				   acList.get(i).setStatus("desligado");
			   }
			}
			
			arcondicionadorepository.save(acList.get(i));		
			Thread.sleep(4000);
		 }
		
		}catch (Exception e) {}
	    
	  		
	   }
	}
	
	
}
