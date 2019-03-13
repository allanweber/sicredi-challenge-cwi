package com.allanweber.sicredi.domain.dto.voting;

import javax.validation.constraints.NotNull;

public class VotingResquestDto {

    public VotingResquestDto() {
    }

    @NotNull
    private String rulingId;

    private Integer expiration = 1;

    public String getRulingId() {
        return rulingId;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setRulingId(String rulingId) {
        this.rulingId = rulingId;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
