package com.nttdata.breno.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TemperaturaAtualTest {	
	
	@Test
	void testGetTemperatura() {
		TemperaturaAtual temp = new TemperaturaAtual();
		
		String tempStr = temp.getTemperatura();
		
		System.out.println("A temperatura Atual é:" + temp.getTemperatura());
		
		//Modificar o valor segundo a temperatura atual
		assertEquals("19.61", tempStr);
	}

}
