package com.allanweber.sicredi.domain.dto.ruling;

import javax.validation.constraints.NotNull;

public class RulingResponseDto extends RulingRequestDto {

    private String id;

    public RulingResponseDto() {
        super();
    }

    public RulingResponseDto(@NotNull String name) {
        super(name);
    }

    public RulingResponseDto(@NotNull String name, String id) {
        super(name);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
