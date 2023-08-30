package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AmazonFileMapperTest {

    private AmazonFileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AmazonFileMapper.class);
    }

    @Test
    public void testToModelWhenInputsProvidedThenCorrectlyMapped() {
        var fileName = "testFile";
        var owner = new User();
        var time = LocalDateTime.now();

        var amazonFile = mapper.toModel(fileName, owner, time);

        assertEquals(fileName, amazonFile.getFileName());
        assertEquals(owner, amazonFile.getOwner());
        assertEquals(time, amazonFile.getUploadedOn());
    }

    @Test
    public void testToModelWhenNullInputsProvidedThenNullProperties() {
        var amazonFile = mapper.toModel(null, null, null);

       assertNull(amazonFile);
    }

    @Test
    public void testToModelWhenEmptyFileNameProvidedThenEmptyFileName() {
        var fileName = "";
        var owner = new User();
        var time = LocalDateTime.now();

        var amazonFile = mapper.toModel(fileName, owner, time);

        assertEquals(fileName, amazonFile.getFileName());
        assertEquals(owner, amazonFile.getOwner());
        assertEquals(time, amazonFile.getUploadedOn());
    }
}