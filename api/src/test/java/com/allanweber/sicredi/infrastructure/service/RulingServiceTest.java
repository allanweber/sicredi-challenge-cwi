package com.allanweber.sicredi.infrastructure.service;

import com.allanweber.sicredi.config.ModelMapperConfig;
import com.allanweber.sicredi.domain.dto.ruling.RulingResponseDto;
import com.allanweber.sicredi.domain.entity.Ruling;
import com.allanweber.sicredi.domain.exception.DataNotFoundedException;
import com.allanweber.sicredi.infrastructure.repository.RulingRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RulingServiceTest {

    @Mock
    private RulingRepository repository;
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    public RulingService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "mapper", new ModelMapperConfig().modelMapper());
    }

    @Test
    public void shouldThrowExceptionWhenRulingRepositoryIsNull() {
        expectedException.expectMessage("RulingRepository is required.");
        expectedException.expect(NullPointerException.class);
        new RulingService(null, null);
    }

    @Test
    public void shouldThrowExceptionWhenModelMapperIsNull() {
        expectedException.expectMessage("ModelMapper is required.");
        expectedException.expect(NullPointerException.class);
        new RulingService(repository, null);
    }

    @Test
    public void shouldReturnListOfRulings() {

        Ruling ruling1 = new Ruling("1");
        ReflectionTestUtils.setField(ruling1, "id", new ObjectId());

        Ruling ruling2 = new Ruling("2");
        ReflectionTestUtils.setField(ruling2, "id", new ObjectId());

        List<Ruling> rulingsEntity = Arrays.asList(ruling1, ruling2);

        Mockito.when(repository.findAll()).thenReturn(rulingsEntity);

        List<RulingResponseDto> response = service.getRulings();

        assertEquals(2, response.size());
        assertFalse(response.get(0).getId().isEmpty());
    }

    @Test
    public void shouldCreateRuling() {
        String name = "ruling";
        Ruling rulingEntity = new Ruling(name);
        ReflectionTestUtils.setField(rulingEntity, "id", new ObjectId());
        Mockito.when(repository.insert(new Ruling(name))).thenReturn(rulingEntity);

        RulingResponseDto response = service.createRuling(new RulingResponseDto(name));

        assertEquals(rulingEntity.getId().toHexString(), response.getId());
    }

    @Test
    public void whenGetRulingNotExistisShouldReturnDataNotFoundedException(){
        expectedException.expect(DataNotFoundedException.class);

        ObjectId objectId = new ObjectId();

        Mockito.when(repository.findById(objectId)).thenThrow(new DataNotFoundedException("Ruling id not founded"));

        service.getRuling(objectId.toHexString());
    }

    @Test
    public void whenGetWithValidIdShouldReturnRulingResponseDto(){
        ObjectId objectId = new ObjectId();
        Ruling entity = new Ruling("name");
        ReflectionTestUtils.setField(entity, "id", new ObjectId());

        Mockito.when(repository.findById(objectId)).thenReturn(Optional.of(entity));

        RulingResponseDto response = service.getRuling(objectId.toHexString());
        assertEquals(entity.getId().toHexString(), response.getId());
    }
}