package com.nttdata.breno.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;
import com.google.gson.reflect.*;

import net.bytebuddy.description.type.TypeVariableToken;

public class TemperaturaAtual {

	
	private String temperatura;

	public Map<String, Object> jsonToMap( String str) {
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
		return map;
	}
	
	public String getTemperatura() {
		
		String urlString = "https://api.openweathermap.org/data/2.5/weather?q=Varginha&lang=pt_br&appid=c50cfbf22593801d88e8af373ef46f65&units=metric";
		try {
			
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine())!= null) {				
				result.append(line);
			}
			
			
			rd.close();;
			
			
			Map<String,Object> respMap = jsonToMap(result.toString());
			Map<String,Object> mainMap = jsonToMap(respMap.get("main").toString());
			
			temperatura = mainMap.get("temp").toString();
					
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		return temperatura;
	}

	
	
	
}
