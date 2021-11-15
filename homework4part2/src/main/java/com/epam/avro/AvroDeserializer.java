package com.epam.avro;

import com.epam.schemas.SchemaEnum;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AvroDeserializer implements Deserializer<String> {

    @Override
    public String deserialize(String schemaId, byte[] bytes) {
        Schema selectedSchema = new Schema.Parser().parse(SchemaEnum.valueOf(schemaId).getSchemaString());
        DatumReader<String> datumReader = new GenericDatumReader<>(selectedSchema);
        SeekableByteArrayInput arrayInput = new SeekableByteArrayInput(bytes);
        String record = null;

        DataFileReader<String> dataFileReader = null;
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
