package com.dronjava.dron.service;

import com.dronjava.dron.dto.DronDto;
import com.dronjava.dron.dto.MedicationDronDto;
import com.dronjava.dron.models.Dron;
import com.dronjava.dron.models.Medication;
import com.dronjava.dron.repository.DronRepository;
import com.dronjava.dron.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DronService {

    @Autowired
    private final DronRepository dronRepository;
    private final MedicationRepository medicationRepository;

    public DronService(DronRepository dronRepository, MedicationRepository medicationRepository) {
        this.dronRepository = dronRepository;
        this.medicationRepository = medicationRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(DronService.class);
    public Dron newDron(Dron dron){
        return dronRepository.save(dron);
    }

    public Dron addMedicationToDron(String idDron, MedicationDronDto medicationDronDto){
        Optional<Dron> dronById = dronRepository.findById(idDron);
        if (dronById.isPresent()) {
            List<String> newMedications = medicationDronDto.getNewMedications();
            boolean validate = this.validateWeight(dronById.get().getIdMedications(),newMedications,dronById.get().getWeightLimit());
            if (validate){
                for (int i = 0; i < newMedications.size(); i++) {
                    dronById.get().setIdMedications(append(dronById.get().getIdMedications(), newMedications.get(i)));
                }
            }
            Update update = new Update();
            update.set("model", dronById.get().getModel());
            update.set("bateryCapacity", dronById.get().getBateryCapacity());
            update.set("serialNumber", dronById.get().getSerialNumber());
            update.set("state", dronById.get().getState());
            update.set("idMedications", dronById.get().getIdMedications());
            dronRepository.findOneAndUpdate(idDron, update);
            return dronRepository.findById(idDron).get();
        } else {
            // manejar el caso en el que no se encuentra el usuario
            return null;
        }
    }
    private static String[] append(String[] arr, String element)
    {
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        list.add(element);

        return list.toArray(new String[0]);
    }

    private boolean validateWeight(String[] medicationList ,List<String> idNewMedications, int weightLimit){
        boolean result = false;
        Optional<Medication> med ;
        int totalWeight = 0;
       for (int i = 0; i < medicationList.length; i++) {
          med =  medicationRepository.findById(medicationList[i]);
           totalWeight = totalWeight + med.get().getWeight();
       }
        for (int i = 0; i < idNewMedications.size(); i++) {
            med =  medicationRepository.findById(idNewMedications.get(i));
            if(med.get().getWeight() + totalWeight >= weightLimit){
                throw new Error ("Maximum weight exceeded");
            }else{
                totalWeight = totalWeight + med.get().getWeight();
                result = true;
            }

        }
        return result;
    }

    public List<Dron> getAllDrons() {
        return dronRepository.findAll();
    }

    public Dron getDronById(String id) {
        Optional<Dron> getDronById = dronRepository.findById(id);
        return getDronById.orElse(null);
    }

    public List<Dron> getDronsBySate(DronDto dronDto) {
        List<Dron> getDronById = dronRepository.findByState(dronDto.getState());
        return getDronById;
    }

    public Dron updateStateDron(String id, DronDto dronDto) throws Exception {
        Optional<Dron> dronById = dronRepository.findById(id);
        if (dronById.isPresent()) {
            if(dronById.get().getIdMedications().length == 0){
                throw new Exception("The status cannot be changed to loading if the drone has not been loaded with medicines");
            }
            if (dronById.get().getBateryCapacity() < 25 && dronDto.getState().equals("LOADING") ){
                throw new Exception("Please recharge the drone");
            }else{
                Update update = this.createObjectUpdate(dronById, dronDto.getState());
                dronRepository.findOneAndUpdate(id, update);
            }
        }
        return dronRepository.findById(id).orElse(null);
    }

    private Update createObjectUpdate (Optional<Dron> dronById, String state){
        Update update = new Update();
        update.set("model", dronById.get().getModel());
        update.set("bateryCapacity", dronById.get().getBateryCapacity());
        update.set("serialNumber", dronById.get().getSerialNumber());
        update.set("state", state);
        update.set("idMedications", dronById.get().getIdMedications());
        return  update;
    }

        @Scheduled(cron = "0 0 * * *")
        public void executeTask() {
            logger.info(dronRepository.findAll().toString());
        }

}
