package com.allanweber.sicredi.infrastructure.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

    public static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    public static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    private String status;

    public UserResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
