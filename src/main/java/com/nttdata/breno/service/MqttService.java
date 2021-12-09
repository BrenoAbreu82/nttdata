package com.nttdata.breno.service;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;


public class MqttService {
	
	final String host = "e7e47c1ebd724bd8aeb212817d48a0ca.s1.eu.hivemq.cloud";
    final String username = "BrenoAbreu";
    final String password = "Alice21@";
    
  //create an MQTT client
    final Mqtt5BlockingClient client = MqttClient.builder()
            .useMqttVersion5()
            .serverHost(host)
            .serverPort(8883)
            .sslWithDefaultConfig()
            .buildBlocking();
    
    public void mqttConnection() {
    	client.connectWith()
        .simpleAuth()
        .username(username)
        .password(UTF_8.encode(password))
        .applySimpleAuth()
        .send();    	
        System.out.println("Connected successfully");
    }
    
    //subscribe to the topic "my/test/topic"
    public void subscribeWith (String topic) {
    client.subscribeWith()
            .topicFilter(topic)
            .send();
    }
    
  //publish a message to the topic "my/test/topic"
    
    public void publishWith(String topic, String payload) {
    
    	 client.toAsync().publishes(ALL, publish -> {
             System.out.println("Received message: " + publish.getTopic() + " -> " + UTF_8.decode(publish.getPayload().get()));

             //disconnect the client after a message was received
             client.disconnect();
         });	
    	
    client.publishWith()
            .topic(topic)
            .payload(UTF_8.encode(payload))
            .send();
    }
    

}
