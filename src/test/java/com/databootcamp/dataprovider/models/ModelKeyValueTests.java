package com.databootcamp.dataprovider.repositories;

import com.databootcamp.dataprovider.models.ModelKeyValue;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import static junit.framework.Assert.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.UUID;

@RunWith(SpringRunner.class)
public class ModelKeyValueTests {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void checkNormalEntity() {
        // given:
        ModelKeyValue entity = new ModelKeyValue("test", "toto");
        // when:
        Set<ConstraintViolation<ModelKeyValue>> violations = validator.validate(entity);
        // then:
        assertTrue(violations.isEmpty());
        assertEquals(entity.getKey(), "test");
        assertEquals(entity.getValue(), "toto");

        entity.setKey("test1");
        violations = validator.validate(entity);
        assertTrue(violations.isEmpty());
        assertEquals(entity.getValue(), "toto");
        assertEquals(entity.getKey(), "test1");

        entity.setKey("test2");
        entity.setValue("titi");
        violations = validator.validate(entity);
        assertTrue(violations.isEmpty());
        assertEquals(entity.getKey(), "test2");
        assertEquals(entity.getValue(), "titi");
    }

    @Test
    public void checkRefusedEntities() {
        ModelKeyValue entity = new ModelKeyValue("", "toto");
        assertEquals(entity.getKey(), "");
        assertEquals(entity.getValue(), "toto");
        Set<ConstraintViolation<ModelKeyValue>> violations = violations = validator.validate(entity);
        assertEquals(violations.size(), 1);
        ConstraintViolation<ModelKeyValue> violation = violations.iterator().next();
        assertEquals("Key shall have a size less than 64 characters and not empty", violation.getMessage());
        assertEquals("key", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());

        entity.setKey(null);
        violations = validator.validate(entity);
        violation = violations.iterator().next();
        assertEquals(violations.size(), 1);
        assertEquals("key", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
        assertEquals("Key cannot be null", violation.getMessage());

        String uuid = UUID.randomUUID().toString();
        String repeatedUUID = IntStream.range(0, 2).mapToObj(i -> uuid).collect(Collectors.joining(""));
        entity.setKey(repeatedUUID);
        violations = validator.validate(entity);
        violation = violations.iterator().next();
        assertEquals(violations.size(), 1);
        assertEquals("key", violation.getPropertyPath().toString());
        assertEquals(repeatedUUID, violation.getInvalidValue());
        assertEquals("Key shall have a size less than 64 characters and not empty", violation.getMessage());

    }
}
