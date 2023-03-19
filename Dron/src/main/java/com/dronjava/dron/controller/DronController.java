package com.dronjava.dron.controller;

import com.dronjava.dron.dto.DronDto;
import com.dronjava.dron.dto.MedicationDronDto;
import com.dronjava.dron.models.Dron;
import com.dronjava.dron.service.DronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dron")
public class DronController {

    @Autowired
    private DronService dronService;

    @PostMapping
    public ResponseEntity<?> newDron(@RequestBody Dron dron){
        try {

            Dron newDron = dronService.newDron(dron);
            return new ResponseEntity<Dron>(newDron, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getDron(){
        try {

            List<Dron> listDron= dronService.getAllDrons();
            return new ResponseEntity<List<Dron>>(listDron, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("{idDron}")
    public ResponseEntity<?> addMedicationToDron(@PathVariable String idDron ,@RequestBody MedicationDronDto medicationDronDto){
        try {
            Dron updatedDron = dronService.addMedicationToDron(idDron, medicationDronDto);
            return new ResponseEntity<Dron>(updatedDron, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateState/{idDron}")
    public ResponseEntity<?> updateStateDron(@PathVariable String idDron ,@RequestBody DronDto dronDto){
        try {
            Dron updatedDron = dronService.updateStateDron(idDron, dronDto);
            return new ResponseEntity<Dron>(updatedDron, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{idDron}")
    public ResponseEntity<?> getDronById(@PathVariable String idDron){
        try {

            Dron getDronById= dronService.getDronById(idDron);
            return new ResponseEntity<Dron>(getDronById, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/state")
    public ResponseEntity<?> getDronsBySate(@RequestBody DronDto dronDto){
        try {

            List<Dron> listDron= dronService.getDronsBySate(dronDto);
            return new ResponseEntity<List<Dron>>(listDron, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
