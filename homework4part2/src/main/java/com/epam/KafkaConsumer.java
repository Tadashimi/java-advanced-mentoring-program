package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private static final String KAFKA_TOPIC = "testTopic1";

    @KafkaListener(topics = KAFKA_TOPIC)
    public void consume(String msg) {
        LOGGER.info(String.format("$$$$ => Consumed message: %s", msg));
    }

}
