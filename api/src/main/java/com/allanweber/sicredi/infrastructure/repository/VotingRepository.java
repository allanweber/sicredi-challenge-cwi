package com.allanweber.sicredi.infrastructure.repository;

import com.allanweber.sicredi.domain.entity.Voting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VotingRepository extends MongoRepository<Voting, ObjectId> {

    List<Voting> getVotingsByExpired(boolean expired);
}