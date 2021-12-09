package com.nttdata.breno.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	
	
	@Scheduled(cron = "* * * ? * *")
	public void reportCurrentTime() {
		
		System.out.println("A hora corrente é = " + dateFormat.format(new Date()));		
	}
	
		
	
}
