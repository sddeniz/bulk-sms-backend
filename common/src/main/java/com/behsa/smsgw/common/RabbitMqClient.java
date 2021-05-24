package com.behsa.smsgw.common;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqClient.class);
    private Connection connection;

    public RabbitMqClient(String hostName, Integer portNumber) {
        try {
            com.rabbitmq.client.ConnectionFactory factory = new ConnectionFactory();
            factory.setAutomaticRecoveryEnabled(true);
            factory.setUsername("ussd_admin");
            factory.setPassword("RabbitMQ2018");
            factory.setVirtualHost("/");
            factory.setHost(hostName);
            factory.setPort(portNumber);
            this.connection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            LOGGER.error("failed on rabbitmq connection", e);
        }
    }

    public Channel getChannel() {
        try {
            if (connection == null) {
                throw new SmsGwException("rabbitmq connection is null");
            }
            return connection.createChannel();
        } catch (IOException e) {
            LOGGER.error("failed on rabbitmq get channel", e);
            throw new SmsGwException("failed on rabbitmq get channel", e);
        }
    }
}
