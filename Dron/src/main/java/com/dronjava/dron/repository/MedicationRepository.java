package com.dronjava.dron.repository;

import com.dronjava.dron.models.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicationRepository extends MongoRepository<Medication, String> {
}
