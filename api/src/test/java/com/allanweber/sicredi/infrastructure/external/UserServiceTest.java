package com.allanweber.sicredi.infrastructure.external;

import com.allanweber.sicredi.domain.exception.ApiException;
import com.allanweber.sicredi.infrastructure.external.dto.UserResponseDto;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private String url = "http://service.com/";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(restTemplate, url);
    }

    @Test
    public void shouldThrowExceptionWhenRestTemplateIsNull() {
        expectedException.expectMessage("RestTemplate is required.");
        expectedException.expect(NullPointerException.class);
        new UserService(null, null);
    }

    @Test
    public void shouldThrowExceptionWhenServiceUrlIsNull() {
        expectedException.expectMessage("Service url is required.");
        expectedException.expect(NullPointerException.class);
        new UserService(restTemplate, null);
    }

    @Test
    public void shouldThrowExceptionWhenCPFIsNull() {
        expectedException.expectMessage("CPF deve ser informado.");
        expectedException.expect(IllegalArgumentException.class);
        userService.findByCpf(null);
    }

    @Test
    public void shouldThrowExceptionWhenCPFIsEmpty() {
        expectedException.expectMessage("CPF deve ser informado.");
        expectedException.expect(IllegalArgumentException.class);
        userService.findByCpf("");
    }

    @Test
    public void shouldThrowExceptionWhenResponseIsNotFounded() {
        expectedException.expectMessage("CPF não encontrado.");
        expectedException.expect(ApiException.class);

        String cpf = "123456789";

        ResponseEntity<UserResponseDto> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Mockito.when(restTemplate.exchange(url + cpf, HttpMethod.GET, null,
                UserResponseDto.class)).thenReturn(response);

        userService.findByCpf(cpf);
    }

    @Test
    public void shouldThrowExceptionWhenResponseIsNotOk() {
        String cpf = "123456789";
        expectedException.expectMessage("Erro ao buscar usuário na endpoint " + url + cpf + ": 500");
        expectedException.expect(ApiException.class);

        ResponseEntity<UserResponseDto> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(restTemplate.exchange(url + cpf, HttpMethod.GET, null,
                UserResponseDto.class)).thenReturn(response);

        userService.findByCpf(cpf);
    }

    @Test
    public void shouldReturnResponseOk() {
        String cpf = "123456789";
        UserResponseDto expectedUser = new UserResponseDto();
        expectedUser.setStatus(UserResponseDto.ABLE_TO_VOTE);
        ResponseEntity<UserResponseDto> response = new ResponseEntity<>(expectedUser, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(url + cpf, HttpMethod.GET, null,
                UserResponseDto.class)).thenReturn(response);

        UserResponseDto responseDto = userService.findByCpf(cpf);
        assertEquals(expectedUser.getStatus(), responseDto.getStatus());
    }
}