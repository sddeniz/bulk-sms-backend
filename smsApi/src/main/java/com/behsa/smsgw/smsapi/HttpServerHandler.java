package com.behsa.smsgw.smsapi;


import com.behsa.smsgw.common.ExchangeType;
import com.behsa.smsgw.common.RabbitMqClient;
import com.behsa.smsgw.common.SmsGwException;
import com.rabbitmq.client.Channel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class HttpServerHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);
    private RabbitMqClient rabbitMqClient = new RabbitMqClient("172.18.60.158", 5672);
    private static String EXCHANGE_NAME = "smsGateway_ex";
    private static String ROUTING_KEY = "smsGateway/request";
    private Channel channel;
    private BufferedReader bufferedReader;

    public HttpServerHandler() {
        try {
            this.channel = rabbitMqClient.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT.getExchangeName(), true);

        } catch (IOException e) {
            throw new SmsGwException("Failed on declare exchange ", e);
        }
    }

    @Override
    public void handle(HttpExchange exchange) {
        String messageId = UUID.randomUUID().toString();
        var client = HttpClient.newHttpClient();

        try {
            CustomMessage customMessage = new CustomMessage();
            JSONObject obj = new JSONObject();


            obj.put("message", customMessage.getMessage());
            obj.put("MSISDN", customMessage.getMSISDN());
            obj.put("smsNo", customMessage.getSmsNo());
            obj.put("Id", messageId);

            String response = "This is the response";
            long threadId = Thread.currentThread().getId();
            System.out.println("I am thread " + threadId);
            response = response + "Thread Id = " + threadId;


            this.channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, obj.toJSONString().getBytes());
            System.out.println(" [x] Sent '" + obj.toJSONString() + "'");
            OutputStream outputStream = exchange.getResponseBody();
            byte[] bytes = messageId.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            outputStream.write(bytes);
            outputStream.close();
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            System.out.println(content.toString());

        } catch (IOException e) {
            LOGGER.error("Failed to publish message", e);
        }

    }

}