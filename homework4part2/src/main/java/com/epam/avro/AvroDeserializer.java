package com.epam.avro;

import com.epam.schemas.SchemaRepository;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class AvroDeserializer implements Deserializer {

    private static final Schema SCHEMA = new Schema.Parser().parse(SchemaRepository.getSchema());

    @Override
    public Object deserialize(String s, byte[] bytes) {
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(SCHEMA);
        SeekableByteArrayInput arrayInput = new SeekableByteArrayInput(bytes);
        GenericRecord record = null;

        DataFileReader<GenericRecord> dataFileReader = null;
        try {
            dataFileReader = new DataFileReader<>(arrayInput, datumReader);
            if (dataFileReader.hasNext()) {
                record = dataFileReader.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }
}
