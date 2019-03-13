package com.allanweber.sicredi.domain.dto.ruling;

import javax.validation.constraints.NotNull;

public class RulingRequestDto {

    @NotNull
    private String name;

    public RulingRequestDto() {
    }

    public RulingRequestDto(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
