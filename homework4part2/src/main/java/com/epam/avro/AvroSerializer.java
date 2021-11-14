package com.epam.avro;

import com.epam.schemas.SchemaRepository;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializer implements Serializer<GenericRecord> {

    private static final Schema SCHEMA = new Schema.Parser().parse(SchemaRepository.getSchema());

    @Override
    public byte[] serialize(String s, GenericRecord genericRecord) {
        byte[] retVal = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GenericDatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(SCHEMA);

        DataFileWriter dataFileWriter = new DataFileWriter<>(datumWriter);
        try {
            dataFileWriter.create(SCHEMA, outputStream);
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
