package com.behsa.smsgw.smsrequestpersist;

import com.behsa.smsgw.common.ExchangeType;
import com.behsa.smsgw.common.RabbitMqClient;
import com.behsa.smsgw.common.SmsGwException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Consumer1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer1.class);
    private final RabbitMqClient rabbitMqClient = new RabbitMqClient("172.18.60.158", 5672);
    private final static String QUEUE_NAME = "smsGateway/listener";
    public static String EXCHANGE_NAME = "smsGateway_ex";
    public static String ROUTING_KEY = "smsGateway/listener";

    public Consumer1() {
//        Channel channel = rabbitMqClient.getChannel();
//        boolean autoAck = false;
//        new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag,
//                                       Envelope envelope,
//                                       AMQP.BasicProperties properties,
//                                       byte[] body)
//                    throws IOException {
//                ROUTING_KEY  = envelope.getRoutingKey();
//                String contentType = properties.getContentType();
//                long deliveryTag = envelope.getDeliveryTag();
//                // (process the message components here ...)
//                channel.basicAck(deliveryTag, false);
//            }
//        };
//






        try {
            Channel channel = rabbitMqClient.getChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.DIRECT.getExchangeName(), true);

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            LOGGER.info(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                LOGGER.info("received new message {}", message);
            }, consumerTag -> {
            });

        } catch (IOException e) {
            throw new SmsGwException("error in make consume", e);
        }

    }




}
