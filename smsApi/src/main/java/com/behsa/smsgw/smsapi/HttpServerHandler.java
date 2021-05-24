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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class HttpServerHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);
    private RabbitMqClient rabbitMqClient = new RabbitMqClient("172.18.60.158", 5672);
    private static String EXCHANGE_NAME = "smsGateway_ex";
    private static String ROUTING_KEY = "smsGateway/request";
    private Channel channel;

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
        try {

            JSONObject obj = new JSONObject();

            obj.put("message", String.format("slm"));
            obj.put("MSISDN", "9120847906");
            obj.put("smsNo", "8080");
            obj.put("Id", messageId);

            this.channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, obj.toJSONString().getBytes());
            System.out.println(" [x] Sent '" + obj.toJSONString() + "'");
            OutputStream outputStream = exchange.getResponseBody();
            byte[] bytes = messageId.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            outputStream.write(bytes);
            outputStream.close();

        } catch (IOException e) {
            LOGGER.error("Failed to publish message", e);
        }

    }

}