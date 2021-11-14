package com.epam.schemas;

public final class SchemaRepository {
    private static final String SCHEMA = "{\"namespace\": \"avro\",\n" +
            "\"type\": \"record\",\n" +
            "\"name\": \"Message\",\n" +
            "\"fields\": [\n" +
            "     {\"name\": \"id\", \"type\": \"string\"},\n" +
            "     {\"name\": \"text\",  \"type\": \"string\"}\n" +
            "]\n" +
            "}\n";


    public static String getSchema() {
        return SCHEMA;
    }
}
