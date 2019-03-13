package com.allanweber.sicredi.domain.service;

import com.allanweber.sicredi.domain.dto.ruling.RulingRequestDto;
import com.allanweber.sicredi.domain.dto.ruling.RulingResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRulingService {

    List<RulingResponseDto> getRulings();

    RulingResponseDto getRuling(@NotNull String rulingId);

    RulingResponseDto createRuling(@Valid RulingRequestDto dto);
}
