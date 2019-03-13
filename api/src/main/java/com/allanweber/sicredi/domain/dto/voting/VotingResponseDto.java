package com.allanweber.sicredi.domain.dto.voting;

import com.allanweber.sicredi.domain.dto.ruling.RulingResponseDto;
import com.allanweber.sicredi.domain.entity.Vote;

import java.util.List;

public class VotingResponseDto {

    public VotingResponseDto() {
    }

    private String id;

    private RulingResponseDto ruling;

    private Integer expiration;

    private Boolean expired;

    private List<Vote> votes;

    private String expirationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RulingResponseDto getRuling() {
        return ruling;
    }

    public void setRuling(RulingResponseDto ruling) {
        this.ruling = ruling;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
