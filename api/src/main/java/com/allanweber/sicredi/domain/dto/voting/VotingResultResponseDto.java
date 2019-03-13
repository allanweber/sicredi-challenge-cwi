package com.allanweber.sicredi.domain.dto.voting;

public class VotingResultResponseDto {

    private Long yes;

    private Long no;

    public VotingResultResponseDto() {
    }

    public VotingResultResponseDto(Long yes, Long no) {
        this.yes = yes;
        this.no = no;
    }

    public Long getYes() {
        return yes;
    }

    public Long getNo() {
        return no;
    }
}
