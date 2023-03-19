package com.dronjava.dron.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicationDronDto {

    private String idDron;

    private List<String> newMedications;
}
