package danekerscode.technicaltask.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateMapperTest {

    @Test
    void shouldBeValidWithValidInput() {
        var input = "01012022_123456";
        var expected = LocalDateTime.of(2022, 1, 1, 12, 34, 56);

        var actual = DateMapper.convertToDateTime(input);

        assertEquals(expected, actual, "The returned LocalDateTime is not as expected.");
    }

    @Test
    void shouldBeFailureWithInvalidInput() {
        var input = "incorrect_format";

        assertThrows(DateTimeParseException.class, () -> DateMapper.convertToDateTime(input), "Expected DateTimeParseException to be thrown, but it didn't");
    }
}