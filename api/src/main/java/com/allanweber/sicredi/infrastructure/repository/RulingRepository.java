package com.allanweber.sicredi.infrastructure.repository;

import com.allanweber.sicredi.domain.entity.Ruling;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RulingRepository extends MongoRepository<Ruling, ObjectId> {
}
