package com.epam;

import com.epam.schemas.SchemaRepository;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String KAFKA_TOPIC = "testTopic1";
    private static final Schema SCHEMA = new Schema.Parser().parse(SchemaRepository.getSchema());

    @Autowired
    private KafkaTemplate<String, GenericRecord> kafkaTemplate;

    public void sendMessage(String msg) {
        LOGGER.info(String.format("$$$$ => Producing message: %s", msg));
        GenericRecord record = new GenericData.Record(SCHEMA);
        record.put(0, "1");
        record.put(1, msg);
        kafkaTemplate.setDefaultTopic(KAFKA_TOPIC);
        ListenableFuture<SendResult<String, GenericRecord>> future = kafkaTemplate.sendDefault("message", record);

        future.addCallback(new ListenableFutureCallback<SendResult<String, GenericRecord>>() {
            @Override
            public void onSuccess(SendResult<String, GenericRecord> result) {
                LOGGER.info("Sent message=[ {} ] with offset=[ {} ]", msg, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Unable to send message=[ {} ] due to : {}", msg, ex.getMessage());
            }
        });
    }
}
