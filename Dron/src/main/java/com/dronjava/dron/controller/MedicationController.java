package com.dronjava.dron.controller;

import com.dronjava.dron.models.Dron;
import com.dronjava.dron.models.Medication;
import com.dronjava.dron.service.DronService;
import com.dronjava.dron.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService ;

    @PostMapping
    public ResponseEntity<?> newMedication(@ModelAttribute Medication medication,
                                           @RequestParam("file")MultipartFile image){
        try {
            Medication newMedication = medicationService.newMedication(medication, image);
            return new ResponseEntity<Medication>(newMedication, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMedications(){
        try {

            List<Medication> listMedication= medicationService.getAllMedications();
            return new ResponseEntity<List<Medication>>(listMedication, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
