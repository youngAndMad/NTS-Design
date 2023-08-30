package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LogMapperTest {

    private LogMapper logMapper;

    @BeforeEach
    void setUp() {
        logMapper = Mappers.getMapper(LogMapper.class);
    }

    @Test
    void toModelShouldBeSuccessWithNonNullParams() {
        var content = "Test content";
        var owner = new User();
        owner.setId(1L);
        owner.setEmail("test@example.com");

        var result = logMapper.toModel(content, owner);

        assertEquals(content, result.getContent());
        assertEquals(owner, result.getOwner());
        assertNull(result.getId());
    }

    @Test
    void toModelWhenShouldBeNullWithNullParams() {
        String content = null;
        User owner = null;

        var result = logMapper.toModel(content, owner);

        assertNull(result);
    }
}