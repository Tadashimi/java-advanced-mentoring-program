package com.epam.schemas;

public enum SchemaEnum {
    SCHEMA_V1("{\"namespace\": \"avro\",\n" +
            "\"type\": \"record\",\n" +
            "\"name\": \"Message\",\n" +
            "\"fields\": [\n" +
            "     {\"name\": \"id\", \"type\": \"string\"},\n" +
            "     {\"name\": \"text\",  \"type\": \"string\"}\n" +
            "]\n" +
            "}\n"),

    SCHEMA_V2("{\"namespace\": \"avro\",\n" +
                      "\"type\": \"record\",\n" +
                      "\"name\": \"Message\",\n" +
                      "\"fields\": [\n" +
                      "     {\"name\": \"id\", \"type\": [\"int\", \"null\"]},\n" +
                      "     {\"name\": \"text\",  \"type\": \"string\"}\n" +
                      "]\n" +
                      "}\n");

    private String schemaString;

    SchemaEnum(String schemaString) {
        this.schemaString = schemaString;
    }

    public String getSchemaString() {
        return schemaString;
    }
}
