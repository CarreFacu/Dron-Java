package com.dronjava.dron.repository;

import com.dronjava.dron.models.Dron;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DronRepository extends MongoRepository<Dron, String> {

    @Query("{'id': ?0}")
    void findOneAndUpdate(String id, Update update);

    List<Dron> findByState(String state);
}
