package com.example.demo.crud;

import com.example.demo.entity.ForexRateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForexRateRepository extends MongoRepository<ForexRateEntity, String> {

}
