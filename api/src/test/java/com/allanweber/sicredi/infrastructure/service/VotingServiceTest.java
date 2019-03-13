package com.allanweber.sicredi.infrastructure.service;

import com.allanweber.sicredi.config.ModelMapperConfig;
import com.allanweber.sicredi.domain.dto.voting.VotingResponseDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResquestDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResultResponseDto;
import com.allanweber.sicredi.domain.entity.Answer;
import com.allanweber.sicredi.domain.entity.Ruling;
import com.allanweber.sicredi.domain.entity.Vote;
import com.allanweber.sicredi.domain.entity.Voting;
import com.allanweber.sicredi.domain.exception.ApiException;
import com.allanweber.sicredi.domain.exception.DataNotFoundedException;
import com.allanweber.sicredi.domain.exception.UserAreadyVotedException;
import com.allanweber.sicredi.infrastructure.external.UserService;
import com.allanweber.sicredi.infrastructure.external.dto.UserResponseDto;
import com.allanweber.sicredi.infrastructure.repository.RulingRepository;
import com.allanweber.sicredi.infrastructure.repository.VotingRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class VotingServiceTest {

    @Mock
    private VotingRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private RulingRepository rulingRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private VotingService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "mapper", new ModelMapperConfig().modelMapper());
    }

    @Test
    public void shouldThrowExceptionWhenVotingRepositoryIsNull() {
        expectedException.expectMessage("VotingRepository is required.");
        expectedException.expect(NullPointerException.class);
        new VotingService(null, null, null, null);
    }

    @Test
    public void shouldThrowExceptionWhenRulingRepositoryIsNull() {
        expectedException.expectMessage("RulingRepository is required.");
        expectedException.expect(NullPointerException.class);
        new VotingService(repository, null, null, null);
    }

    @Test
    public void shouldThrowExceptionWhenModelMapperIsNull() {
        expectedException.expectMessage("ModelMapper is required.");
        expectedException.expect(NullPointerException.class);
        new VotingService(repository, rulingRepository, null, null);
    }

    @Test
    public void shouldThrowExceptionWhenUserServiceIsNull() {
        expectedException.expectMessage("UserService is required.");
        expectedException.expect(NullPointerException.class);
        new VotingService(repository, rulingRepository, mapper, null);
    }

    @Test
    public void shouldGetListOfVotings(){
        Ruling ruling = new Ruling( "name");
        Voting voting1 = new Voting(ruling, 1);
        Voting voting2 = new Voting(ruling, 1);
        List<Voting> votings = Arrays.asList(voting1, voting2);

        Mockito.when(repository.findAll()).thenReturn(votings);

        List<VotingResponseDto> response = service.getVotings();
        assertEquals(2, response.size());
    }

    @Test
    public void shouldThrowExceptionWhenVotingNotFounded(){
        expectedException.expectMessage("Votação não encontrada");
        expectedException.expect(DataNotFoundedException.class);
        ObjectId objectId = new ObjectId();
        Mockito.when(repository.findById(objectId)).thenThrow(new DataNotFoundedException("Votação não encontrada"));
        service.getVoting(objectId.toHexString());
    }

    @Test
    public void shouldReturnVoting(){
        ObjectId objectId = new ObjectId();
        Voting voting = new Voting(new Ruling("name"), 1);
        ReflectionTestUtils.setField(voting, "id", objectId);

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        VotingResponseDto response = service.getVoting(objectId.toHexString());
        assertEquals(response.getId(), voting.getId().toHexString());
    }

    @Test
    public void shouldThrowExceptionWhenCreatingVotingWithInvalidRuling(){
        expectedException.expectMessage("Pauta não encontrada.");
        expectedException.expect(DataNotFoundedException.class);
        ObjectId objectId = new ObjectId();

        Mockito.when(rulingRepository.findById(objectId)).thenThrow(new DataNotFoundedException("Pauta não encontrada."));

        VotingResquestDto dto = new VotingResquestDto();
        dto.setRulingId(objectId.toHexString());

        service.createVoting(dto);
    }

    @Test
    public void shouldCreateNewVoting(){

        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);

        Voting voting = new Voting(ruling, 1);

        VotingResquestDto request = new VotingResquestDto();
        request.setRulingId(objectId.toHexString());
        request.setExpiration(1);

        Mockito.when(rulingRepository.findById(objectId)).thenReturn(Optional.of(ruling));

        Mockito.when(repository.insert(voting)).thenReturn(voting);

        VotingResponseDto response = service.createVoting(request);

        assertEquals(response.getRuling().getId(), voting.getRuling().getId().toHexString());
    }

    @Test
    public void shouldAddVote(){
        String cpf = "23456789123";
        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Vote vote = new Vote(cpf, Answer.SIM);
        Voting voting = new Voting(ruling, 1);

        UserResponseDto expectedUser = new UserResponseDto();
        expectedUser.setStatus(UserResponseDto.ABLE_TO_VOTE);
        Mockito.when(userService.findByCpf(cpf)).thenReturn(expectedUser);

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        Voting votingWithVote = new Voting(ruling,  1);
        votingWithVote.addVote(vote);
        Mockito.when(repository.save(voting)).thenReturn(votingWithVote);

        service.addVote(objectId.toHexString(), vote);
    }

    @Test
    public void whenAddVoteWithInvalidVotingIdShouldReturnException(){
        expectedException.expectMessage("Votação não encontrada");
        expectedException.expect(DataNotFoundedException.class);

        ObjectId objectId = new ObjectId();

        Mockito.when(repository.findById(objectId)).thenThrow(new DataNotFoundedException("Votação não encontrada"));

        Vote vote = new Vote("mail@mail.com", Answer.SIM);
        service.addVote(objectId.toHexString(), vote);
    }

    @Test
    public void whenAddEqualVoteShouldReturnException(){
        expectedException.expectMessage("O usuário 23456789123 já votou nessa sessão.");
        expectedException.expect(UserAreadyVotedException.class);

        String cpf = "23456789123";
        UserResponseDto expectedUser = new UserResponseDto();
        expectedUser.setStatus(UserResponseDto.ABLE_TO_VOTE);

        Mockito.when(userService.findByCpf(cpf)).thenReturn(expectedUser);

        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Vote vote = new Vote(cpf, Answer.SIM);
        Voting voting = new Voting(ruling, 1);
        voting.addVote(vote);

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        service.addVote(objectId.toHexString(), new Vote(cpf, Answer.NAO));
    }

    @Test
    public void shouldThrowExceptionWhenUserIsUnableToVote(){
        expectedException.expectMessage("Usuário não está habilitado para votações.");
        expectedException.expect(ApiException.class);


        String cpf = "23456789123";
        UserResponseDto expectedUser = new UserResponseDto();
        expectedUser.setStatus(UserResponseDto.UNABLE_TO_VOTE);

        Mockito.when(userService.findByCpf(cpf)).thenReturn(expectedUser);

        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Voting voting = new Voting(ruling, 1);

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        service.addVote(objectId.toHexString(), new Vote(cpf, Answer.NAO));
    }

    @Test
    public void shouldCountVotes(){
        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Voting voting = new Voting(ruling, 1);

        for (int i = 0; i < 100; i++) {
            voting.addVote(
                    new Vote(String.format("meuemail%s@mail.com", i),
                            i % 2 == 0? Answer.SIM : Answer.NAO)
            );
        }

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        VotingResultResponseDto result = service.getResult(objectId.toHexString());

        assertEquals(50, result.getYes().intValue());
        assertEquals(50, result.getNo().intValue());
    }

    @Test
    public void shouldCountVotesZero(){
        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Voting voting = new Voting(ruling, 1);

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));

        VotingResultResponseDto result = service.getResult(objectId.toHexString());

        assertEquals(0, result.getYes().intValue());
        assertEquals(0, result.getNo().intValue());
    }

    @Test
    public void addVotingExpiredShouldReturnException(){
        expectedException.expectMessage("Essa sessão de votação está finalizada.");
        expectedException.expect(ApiException.class);
        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Voting voting = new Voting(ruling, 1);
        ReflectionTestUtils.setField(voting, "expirationDate", DateUtils.addMinutes(new Date(), -2));

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(voting));
        Mockito.when(repository.save(voting)).thenReturn(voting);

        service.addVote(objectId.toHexString(), new Vote("mail@mail.com", Answer.NAO));
    }

    @Test
    public void shouldReturnNotExpiredVoting(){
        ObjectId objectId = new ObjectId();
        Ruling ruling = new Ruling("name");
        ReflectionTestUtils.setField(ruling, "id", objectId);
        Voting voting = new Voting(ruling, 1);
        ReflectionTestUtils.setField(voting, "id", objectId);
        List<Voting> votings = Arrays.asList(voting);

        Mockito.when(repository.getVotingsByExpired(false)).thenReturn(votings);

        List<VotingResponseDto> response = service.getVotingsNotExpired();

        assertEquals(objectId.toHexString(), response.get(0).getId());
    }
}