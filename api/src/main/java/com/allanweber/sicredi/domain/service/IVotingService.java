package com.allanweber.sicredi.domain.service;

import com.allanweber.sicredi.domain.dto.voting.VotingResponseDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResquestDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResultResponseDto;
import com.allanweber.sicredi.domain.entity.Vote;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IVotingService {

    List<VotingResponseDto> getVotings();

    List<VotingResponseDto> getVotingsNotExpired();

    VotingResponseDto getVoting(@NotNull String votingId);

    VotingResponseDto createVoting(@Valid VotingResquestDto dto);

    void addVote(@NotNull String votingId, @Valid Vote vote);

    VotingResultResponseDto getResult(@NotNull String votingId);
}
