package com.allanweber.sicredi.domain.entity;

import com.allanweber.sicredi.domain.exception.UserAreadyVotedException;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "voting")
public class Voting {

    @Id
    private ObjectId id;

    @NotNull
    private Ruling ruling;

    @NotNull
    private Integer expiration;

    private Boolean expired;

    private List<Vote> votes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expirationDate;

    public Voting(@NotNull Ruling ruling, @NotNull Integer expiration) {
        this.ruling = ruling;
        this.expiration = expiration;
        this.expired = false;
        this.votes = new ArrayList<>();
        this.expirationDate = DateUtils.addMinutes(new Date(), expiration);
    }

    public ObjectId getId() {
        return id;
    }

    public Ruling getRuling() {
        return ruling;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void addVote(Vote vote) {
        if (votes.stream().anyMatch(vt -> vt.getCpf().equals(vote.getCpf()))) {
            throw new UserAreadyVotedException(vote.getCpf());
        }
        votes.add(vote);
    }

    public Boolean isExpired() {

        if (!expired && expirationDate.before(new Date())) {
            expired = true;
        }

        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voting voting = (Voting) o;
        return Objects.equals(id, voting.id) &&
                Objects.equals(ruling, voting.ruling) &&
                Objects.equals(expiration, voting.expiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ruling, expiration);
    }
}
