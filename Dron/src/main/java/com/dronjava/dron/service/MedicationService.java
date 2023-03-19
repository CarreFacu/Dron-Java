package com.dronjava.dron.service;

import com.dronjava.dron.models.Medication;
import com.dronjava.dron.repository.MedicationRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication newMedication(Medication medication, MultipartFile image) throws IOException {
            byte[] bytes = image.getBytes();
            Medication newMedication = new Medication();
            newMedication.setCode(medication.getCode());
            newMedication.setName(medication.getName());
            newMedication.setWeight(medication.getWeight());
            newMedication.setImage(bytes);

            return medicationRepository.save(newMedication);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
}
