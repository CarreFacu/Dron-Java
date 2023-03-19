package com.dronjava.dron.models;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;

@Data
@Document(collection = "Medication")
public class Medication {

    @Id
    private String id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;
    private int weight;
    @Pattern(regexp = "^[A-Z0-9_]*$")
    private String code;
    private byte[] image;
}
