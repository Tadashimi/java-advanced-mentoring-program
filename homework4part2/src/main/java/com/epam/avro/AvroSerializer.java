package com.epam.avro;

import com.epam.schemas.SchemaEnum;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class AvroSerializer implements Serializer<GenericRecord> {

    @Override
    public byte[] serialize(String schemaId, GenericRecord genericRecord) {
        SchemaEnum selectedSchema = SchemaEnum.valueOf(schemaId);
        Schema schema = new Schema.Parser().parse(selectedSchema.getSchemaString());
        byte[] retVal = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);

        DataFileWriter dataFileWriter = new DataFileWriter<>(datumWriter);
        try {
            dataFileWriter.create(schema, outputStream);
            dataFileWriter.append(genericRecord);
            dataFileWriter.flush();
            dataFileWriter.close();
            retVal = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
