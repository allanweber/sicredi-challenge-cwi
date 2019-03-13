package com.allanweber.sicredi.infrastructure.jobs;

import com.allanweber.sicredi.domain.entity.Voting;
import com.allanweber.sicredi.infrastructure.repository.VotingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Component
public class ExpireJob {
    private static final Logger log = LoggerFactory.getLogger(ExpireJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private MongoTemplate mongoTemplate;

    private VotingRepository repository;

    @Autowired
    public ExpireJob(MongoTemplate mongoTemplate, VotingRepository repository) {
        this.mongoTemplate = Objects.requireNonNull(mongoTemplate, "MongoTemplate is required.");
        this.repository = Objects.requireNonNull(repository, "VotingRepository is required.");
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void reportCurrentTime() {
        log.info("Inciando a busca por votações expiradas {}", dateFormat.format(new Date()));

        Query query = new Query();
        query.addCriteria(Criteria.where("expired").is(false).and("expirationDate").lt(new Date()));

        List<Voting> voting  = mongoTemplate.find(query, Voting.class);

        log.info("Concluída a busca por votações expiradas {}", dateFormat.format(new Date()));

        log.info("Encontradas {} votações expiradas", voting.size());

        if(voting.size() > 0){
            log.info("Iniciando processo de expiração de votações {}", dateFormat.format(new Date()));

            voting.forEach(expireVoting());

            log.info("Processo de expiração de votações concluído {}", dateFormat.format(new Date()));
        }
    }

    private Consumer<Voting> expireVoting(){
        return voting -> {
            voting.isExpired();
            repository.save(voting);
        };
    }
}
