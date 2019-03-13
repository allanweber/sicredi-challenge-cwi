package com.allanweber.sicredi.api;

import com.allanweber.sicredi.domain.dto.voting.VotingResponseDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResquestDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResultResponseDto;
import com.allanweber.sicredi.domain.entity.Answer;
import com.allanweber.sicredi.domain.entity.Vote;
import com.allanweber.sicredi.domain.service.IVotingService;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class VotingControllerTest {

    @Mock
    private IVotingService service;

    @InjectMocks
    private VotingController controller;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenIVotingServiceIsNull(){
        expectedException.expectMessage("IVotingService is required.");
        expectedException.expect(NullPointerException.class);
        new VotingController(null);
    }

    @Test
    public void shouldGetVotings(){

        VotingResponseDto dto = new VotingResponseDto();
        dto.setId("123456");
        Mockito.when(service.getVotings()).thenReturn(Arrays.asList(dto));

        ResponseEntity response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldGetVoting(){

        String id = new ObjectId().toHexString();
        VotingResponseDto dto = new VotingResponseDto();
        dto.setId(id);
        Mockito.when(service.getVoting(id)).thenReturn(dto);

        ResponseEntity response = controller.get(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldGetVotingResult(){

        String id = new ObjectId().toHexString();

        VotingResultResponseDto result = new VotingResultResponseDto(10L,10L);

        Mockito.when(service.getResult(id)).thenReturn(result);

        ResponseEntity response = controller.getResult(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldCreateVoting() throws URISyntaxException {

        String id = new ObjectId().toHexString();

        VotingResponseDto dto = new VotingResponseDto();
        dto.setId(id);

        VotingResquestDto request = new VotingResquestDto();

        Mockito.when(service.createVoting(request)).thenReturn(dto);

        ResponseEntity response = controller.create(request);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldAddVote(){

        String id = new ObjectId().toHexString();
        String userId = "mail@mail.com";
        Answer answer = Answer.NAO;

        Vote vote = new Vote(userId, answer);

        Mockito.doNothing().when(service).addVote(id, vote);

        ResponseEntity response = controller.addVote(userId, id, answer);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void shouldGetNotExpired(){
        String id = new ObjectId().toHexString();
        VotingResponseDto dto = new VotingResponseDto();
        dto.setId(id);
        Mockito.when(service.getVotingsNotExpired()).thenReturn(Collections.singletonList(dto));

        ResponseEntity response = controller.getNotExpired();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}