package com.allanweber.sicredi.api;

import com.allanweber.sicredi.domain.dto.voting.VotingResponseDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResquestDto;
import com.allanweber.sicredi.domain.dto.voting.VotingResultResponseDto;
import com.allanweber.sicredi.domain.entity.Answer;
import com.allanweber.sicredi.domain.entity.Vote;
import com.allanweber.sicredi.domain.exception.ExceptionResponse;
import com.allanweber.sicredi.domain.service.IVotingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequestMapping(value = "v1/voting", produces = "application/json")
@Api(value = "voting", description = "Operations to manage voting")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "An error occurred", response = ExceptionResponse.class)
})
@Validated
@CrossOrigin
public class VotingController {

    private IVotingService service;

    public VotingController(IVotingService service) {
        this.service = Objects.requireNonNull(service, "IVotingService is required.");
    }

    @ApiOperation(value = "Listar todas as sessões de votação", response = VotingResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sessões de votação retornadas com sucesso")
    })
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getVotings());
    }

    @ApiOperation(value = "Listar todas as sessões de votação não expiradas", response = VotingResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sessões de votação não expiradas retornadas com sucesso")
    })
    @GetMapping("/not-expired")
    public ResponseEntity<?> getNotExpired(){
        return ResponseEntity.ok(service.getVotingsNotExpired());
    }

    @ApiOperation(value = "Retornar uma sessão de votação", response = VotingResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sessão de votação retornada com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@NotNull @PathVariable("id") String votingId){
        return ResponseEntity.ok(service.getVoting(votingId));
    }

    @ApiOperation(value = "Retornar o resultado de uma sessão de votação", response = VotingResultResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resultado da sessão de votação retornado com sucesso")
    })
    @GetMapping("/result/{id}")
    public ResponseEntity<?> getResult(@NotNull @PathVariable("id") String votingId){
        return ResponseEntity.ok(service.getResult(votingId));
    }

    @ApiOperation(value = "Criar uma sessão de votação", response = VotingResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sessão de votação criada com sucesso")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VotingResquestDto voting) throws URISyntaxException {
        VotingResponseDto created = service.createVoting(voting);
        return ResponseEntity.created(new URI(created.getId())).body(created);
    }

    @ApiOperation(value = "Adicionar um voto a uma sessão de votação")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Voto adicionado com sucesso")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PutMapping("/vote/{id}/{answer}")
    public ResponseEntity<?> addVote(@NotNull @RequestHeader("user-id") String userId,
                                      @NotNull @PathVariable("id") String votingId,
                                      @Valid @PathVariable("answer") Answer answer) {

        Vote vote = new Vote(userId, answer);
        service.addVote(votingId, vote);
        return ResponseEntity.ok().build();
    }
}
