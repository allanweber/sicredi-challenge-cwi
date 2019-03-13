package com.allanweber.sicredi.infrastructure.external;

import com.allanweber.sicredi.domain.exception.ApiException;
import com.allanweber.sicredi.infrastructure.external.dto.UserResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserService {

    private RestTemplate restTemplate;

    private String url;

    @Autowired
    public UserService(RestTemplate restTemplate, @Value("${integrations.user-service}") String url) {
        this.restTemplate = Objects.requireNonNull(restTemplate, "RestTemplate is required.");
        this.url = Objects.requireNonNull(url, "Service url is required.");
    }

    public UserResponseDto findByCpf(String cpf) {

        if(StringUtils.isBlank(cpf))
            throw new IllegalArgumentException("CPF deve ser informado.");

        String requestUrl = url + cpf;

        ResponseEntity<UserResponseDto> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null,
                UserResponseDto.class);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ApiException("CPF não encontrado.");
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(String.format("Erro ao buscar usuário na endpoint %s: %s", requestUrl,
                    response.getStatusCodeValue()));
        }

        return response.getBody();
    }
}
