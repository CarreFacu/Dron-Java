package com.dronjava.dron.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Document(collection = "Dron")
public class Dron {

    @Id
    private String id;

    @Pattern(regexp = "^(Lightweight|Middleweight|Cruiserweight|Heavyweight)$")
    private String model;
    private int serialNumber;
    private int weightLimit;
    private int bateryCapacity;
    @Pattern(regexp = "^(IDLE|LOADING|LOADED|DELIVERING|DELIVERED|RETURNING)$")
    private String state;

    private String[] idMedications;

}
