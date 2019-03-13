package com.allanweber.sicredi.domain.entity;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

public class RulingTest {

    @Test
    public void shouldValidateEquality(){
        Ruling ruling1 = new Ruling("1");
        Ruling ruling2 = new Ruling("1");

        assertEquals(ruling1, ruling2);

        ReflectionTestUtils.setField(ruling2, "id", new ObjectId());

        assertNotEquals(ruling1, ruling2);
    }

}