package com.epam.schemas;

import com.epam.avro.AvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaConsumerNew {
    private static final String KAFKA_TOPIC = "testTopic1";

    @Autowired
    private AvroDeserializer deserializer;

    public String readMessages() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("group.id", "group_id");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, byte[]> consumerQQQ = new KafkaConsumer<>(props);
        consumerQQQ.subscribe(Collections.singletonList(KAFKA_TOPIC));
        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumerQQQ.poll(Duration.of(1, ChronoUnit.SECONDS));
                for (ConsumerRecord<String, byte[]> record : records)
                {
                    String selectedSchemaId = record.key();
                    byte[] msg = record.value();
                    String deserializedMessage = deserializer.deserialize(selectedSchemaId, msg);
                    System.out.println(deserializedMessage);
                }
            }
        } finally {
            consumerQQQ.close();
        }
    }
}
