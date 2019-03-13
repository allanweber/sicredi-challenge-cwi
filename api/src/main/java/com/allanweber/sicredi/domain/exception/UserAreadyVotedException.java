package com.allanweber.sicredi.domain.exception;

public class UserAreadyVotedException extends RuntimeException {
    public UserAreadyVotedException(String userId) {
        super(String.format("O usuário %s já votou nessa sessão.", userId));
    }
}
