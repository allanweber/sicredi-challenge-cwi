package com.allanweber.sicredi.infrastructure.service;

import com.allanweber.sicredi.domain.dto.voting.VotingResponseDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResquestDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResultResponseDto;
import com.allanweber.sicredi.domain.entity.Answer;
import com.allanweber.sicredi.domain.entity.Ruling;
import com.allanweber.sicredi.domain.entity.Vote;
import com.allanweber.sicredi.domain.entity.Voting;
import com.allanweber.sicredi.domain.exception.ApiException;
import com.allanweber.sicredi.domain.exception.DataNotFoundedException;
import com.allanweber.sicredi.domain.service.IVotingService;
import com.allanweber.sicredi.infrastructure.external.UserService;
import com.allanweber.sicredi.infrastructure.external.dto.UserResponseDto;
import com.allanweber.sicredi.infrastructure.repository.RulingRepository;
import com.allanweber.sicredi.infrastructure.repository.VotingRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class VotingService implements IVotingService {

    private VotingRepository repository;
    private RulingRepository rulingRepository;
    private ModelMapper mapper;
    private UserService userService;

    @Autowired
    public VotingService(VotingRepository repository, RulingRepository rulingRepository,
                         ModelMapper mapper, UserService userService) {
        this.repository = Objects.requireNonNull(repository, "VotingRepository is required.");
        this.rulingRepository = Objects.requireNonNull(rulingRepository, "RulingRepository is required.");
        this.mapper = Objects.requireNonNull(mapper, "ModelMapper is required.");
        this.userService = Objects.requireNonNull(userService, "UserService is required.");
    }

    @Override
    public List<VotingResponseDto> getVotings() {
        List<Voting> votings = repository.findAll();
        return votings.stream().map(voting -> mapper
                .map(voting, VotingResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<VotingResponseDto> getVotingsNotExpired() {
        List<Voting> votings = repository.getVotingsByExpired(false);
        return votings.stream().map(voting -> mapper
                .map(voting, VotingResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public VotingResponseDto getVoting(@NotNull String votingId) {
        Voting voting = getOneVoting(votingId);
        return mapper.map(voting, VotingResponseDto.class);
    }

    @Override
    public VotingResponseDto createVoting(@Valid VotingResquestDto dto) {

        Ruling ruling = rulingRepository.findById(new ObjectId(dto.getRulingId()))
                .orElseThrow(() -> new DataNotFoundedException("Pauta não encontrada."));

        Voting voting = new Voting(ruling, Optional.ofNullable(dto.getExpiration()).orElse(1));
        voting = repository.insert(voting);
        return mapper.map(voting, VotingResponseDto.class);
    }

    @Override
    public void addVote(@NotNull String votingId, @Valid Vote vote) {
        Voting voting = getOneVoting(votingId);

        if(voting.isExpired()){
            repository.save(voting);
            throw new ApiException("Essa sessão de votação está finalizada.");
        }

        UserResponseDto response = userService.findByCpf(vote.getCpf());
        if(response.getStatus().equals(UserResponseDto.UNABLE_TO_VOTE)){
            throw new ApiException("Usuário não está habilitado para votações.");
        }

        voting.addVote(vote);

        repository.save(voting);
    }

    @Override
    public VotingResultResponseDto getResult(@NotNull String votingId) {
        Voting voting = getOneVoting(votingId);

        Long yes = voting.getVotes().stream().filter(vote -> vote.getAnswer().equals(Answer.SIM)).count();
        Long no = voting.getVotes().stream().filter(vote -> vote.getAnswer().equals(Answer.NAO)).count();

        return new VotingResultResponseDto(yes, no);
    }

    private Voting getOneVoting(@NotNull String votingId){
        return repository.findById(new ObjectId(votingId))
                .orElseThrow(() -> new DataNotFoundedException("Votação não encontrada"));
    }
}
