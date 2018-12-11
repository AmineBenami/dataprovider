package com.databootcamp.dataprovider.repositories;

import com.databootcamp.dataprovider.models.ModelKeyValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyValueRepository extends MongoRepository<ModelKeyValue, String> {
}
