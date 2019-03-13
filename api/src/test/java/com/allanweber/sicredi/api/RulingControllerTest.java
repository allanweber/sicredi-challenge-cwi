package com.allanweber.sicredi.api;

import com.allanweber.sicredi.domain.dto.ruling.RulingRequestDto;
import com.allanweber.sicredi.domain.dto.ruling.RulingResponseDto;
import com.allanweber.sicredi.infrastructure.service.RulingService;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RulingControllerTest {

    @Mock
    private RulingService service;

    @InjectMocks
    public RulingController controller;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenRulingServiceIsNull() {
        expectedException.expectMessage("RulingService is required.");
        expectedException.expect(NullPointerException.class);
        new RulingController(null);
    }

    @Test
    public void shouldReturnListOfRulings() {

        List<RulingResponseDto> rulings = Arrays.asList(
                new RulingResponseDto("1", "1"),
                new RulingResponseDto("2", "2")
        );

        Mockito.when(service.getRulings()).thenReturn(rulings);

        ResponseEntity response = controller.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, ((List<RulingResponseDto>) response.getBody()).size());
    }

    @Test
    public void whenGetRulingdShouldReturnRulingResponseDto(){
        String id = "123";

        RulingResponseDto dto = new RulingResponseDto("1", "1");

        Mockito.when(service.getRuling(id)).thenReturn(dto);

        ResponseEntity response = controller.get(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void shouldCreateRuling() throws URISyntaxException {

        String id = new ObjectId().toHexString();

        RulingResponseDto dto = new RulingResponseDto();
        dto.setId(id);

        RulingRequestDto request = new RulingRequestDto();

        Mockito.when(service.createRuling(request)).thenReturn(dto);

        ResponseEntity response = controller.create(request);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}
