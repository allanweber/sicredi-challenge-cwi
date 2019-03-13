package com.allanweber.sicredi.domain.entity;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Vote {

    @NotNull
    private String cpf;

    @NotNull
    private Answer answer;

    public Vote() {
    }

    public Vote(@NotNull String cpf, @NotNull Answer answer) {
        this.cpf = cpf;
        this.answer = answer;
    }

    public String getCpf() {
        return cpf;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(cpf, vote.cpf) &&
                answer == vote.answer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, answer);
    }
}
