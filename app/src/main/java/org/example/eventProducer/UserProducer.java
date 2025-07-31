package org.example.eventProducer;

import org.springframework.beans.factory.annotation.Value;

import org.example.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;


@Service
public class UserProducer {


    private final KafkaTemplate<String, UserDto> kafkaTemplate;
    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;
    @Autowired
    UserProducer(KafkaTemplate<String, UserDto> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventToKafka(UserDto eventData) {
        Message<UserDto> message = MessageBuilder.withPayload(eventData)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME).build();
        kafkaTemplate.send(message);
    }
}
