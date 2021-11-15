package com.epam;

import com.epam.avro.AvroSerializer;
import com.epam.schemas.SchemaEnum;
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

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private AvroSerializer serializer;

    public void sendMessage(String schemaId, String msg) {
        LOGGER.info(String.format("$$$$ => Producing message: %s Schema id: %s", msg, schemaId));
        SchemaEnum selectedSchema = SchemaEnum.valueOf(schemaId);
        Schema schema = new Schema.Parser().parse(selectedSchema.getSchemaString());
        GenericRecord record = new GenericData.Record(schema);
        switch (selectedSchema) {
            case SCHEMA_V1:
                record.put(0, "1");
                break;
            case SCHEMA_V2:
                record.put(0, 1);
        }
        record.put(1, msg);
        kafkaTemplate.setDefaultTopic(KAFKA_TOPIC);
        byte[] serializedMessage = serializer.serialize(schemaId, record);
        ListenableFuture<SendResult<String, byte[]>> future = kafkaTemplate.sendDefault("message", serializedMessage);

        future.addCallback(new ListenableFutureCallback<SendResult<String, byte[]>>() {
            @Override
            public void onSuccess(SendResult<String, byte[]> result) {
                LOGGER.info("Sent message=[ {} ] with offset=[ {} ]", msg, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Unable to send message=[ {} ] due to : {}", msg, ex.getMessage());
            }
        });
    }
}
