package com.nttdata.breno.service;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

//Classe Exemplo extraída do Site HIVEMQ

public class MyMqttMain {

    public static void main(String[] args) throws Exception {

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

        //connect to HiveMQ Cloud with TLS and username/pw
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");

        //subscribe to the topic "my/test/topic"
        client.subscribeWith()
                .topicFilter("my/test/topic")
                .send();

        //set a callback that is called when a message is received (using the async API style)
        client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " + publish.getTopic() + " -> " + UTF_8.decode(publish.getPayload().get()));

            //disconnect the client after a message was received
            client.disconnect();
        });

        //publish a message to the topic "my/test/topic"
        client.publishWith()
                .topic("my/test/topic")
                .payload(UTF_8.encode("Hello"))
                .send();

    }
}