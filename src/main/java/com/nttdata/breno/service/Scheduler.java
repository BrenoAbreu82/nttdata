package com.nttdata.breno.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.nttdata.breno.repository.ArCondicionadoRepository;

//Agendador utilizado na Programação de horário do Ar Condicionado

@Component
@EnableScheduling
public class Scheduler {
	
	@Async
	public void schedulerTask(String horaProgramda, ArCondicionadoRepository rep, Integer id, String funcao) throws InterruptedException {
        Date date = Calendar.getInstance().getTime(); 
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");  
        String strDate = dateFormat.format(date);  
        System.out.println("Converted String: " + strDate);  	
		
		String horaOrigem = horaProgramda;
		
		
		if (horaOrigem.compareTo(strDate)==0){
			
			System.out.println("horaOrigem = "+horaOrigem+" strDate = "+strDate);
		}

		long hora = new Integer(horaOrigem.substring(0, 2)) * 3600000; //1 hora = 3.600.000 milisegundos
		long minuto = Integer.parseInt(horaOrigem.substring(3)) * 60000; //1 minuto = 60.000 milisegundos 

		long strHora = new Integer(strDate.substring(0, 2)) * 3600000; //1 hora = 3.600.000 milisegundos
		long strMinuto = Integer.parseInt(strDate.substring(3)) * 60000; //1 minuto = 60.000 milisegundos 

		long result = hora+minuto;
		long strResult = strHora+strMinuto;
		
		System.out.println("result = "+result+"  strResult = "+strResult);
		
		long fResult = result-strResult;
		
		System.out.println("Resultado final é:"+fResult);
		
		if (fResult==0) {
			System.out.println("Executando em:"+strDate );
			
			rep.getById(id).setStatus(funcao);
			rep.save(rep.getById(id));
			
			}else if (fResult>0) {
				Thread.sleep(fResult);
				System.out.println("Executando em:"+dateFormat.format(Calendar.getInstance().getTime()) );
				rep.getById(id).setStatus(funcao);
				rep.save(rep.getById(id));
				
		}else {
			System.out.println("Horário Incompatível");
		}
		
	};
	
	
}
