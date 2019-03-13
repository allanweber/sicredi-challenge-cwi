package com.allanweber.sicredi.infrastructure.service;

import com.allanweber.sicredi.domain.dto.ruling.RulingRequestDto;
import com.allanweber.sicredi.domain.dto.ruling.RulingResponseDto;
import com.allanweber.sicredi.domain.entity.Ruling;
import com.allanweber.sicredi.domain.exception.DataNotFoundedException;
import com.allanweber.sicredi.domain.service.IRulingService;
import com.allanweber.sicredi.infrastructure.repository.RulingRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Validated
public class RulingService implements IRulingService {

    private RulingRepository repository;
    private ModelMapper mapper;

    @Autowired
    public RulingService(RulingRepository repository, ModelMapper mapper) {
        this.repository = Objects.requireNonNull(repository, "RulingRepository is required.");
        this.mapper = Objects.requireNonNull(mapper, "ModelMapper is required.");
    }

    @Override
    public List<RulingResponseDto> getRulings() {
        List<Ruling> rulings = repository.findAll();
        return rulings.stream().map(ruling -> mapper
                .map(ruling, RulingResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public RulingResponseDto getRuling(@NotNull String rulingId) {
        Ruling ruling = repository.findById(new ObjectId(rulingId))
                .orElseThrow(() -> new DataNotFoundedException("Pauta não encontrada."));
        return mapper.map(ruling, RulingResponseDto.class);
    }

    @Override
    public RulingResponseDto createRuling(@Valid RulingRequestDto dto) {
        Ruling ruling = new Ruling(dto.getName());
        ruling = repository.insert(ruling);
        return mapper.map(ruling, RulingResponseDto.class);
    }
}
