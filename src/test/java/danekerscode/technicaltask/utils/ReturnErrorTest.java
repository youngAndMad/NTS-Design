package danekerscode.technicaltask.utils;

import danekerscode.technicaltask.exception.InvalidUserPropertiesException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReturnErrorTest {

    @Test
    void testValidateRequestWithNoErrors() {
        var br = mock(BindingResult.class);
        when(br.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() -> ReturnError.validateRequest(br));
    }

    @Test
    void testValidateRequestWithErrors() {
        var bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        var fieldError1 = new FieldError("objectName", "fieldName1", "Error message 1");
        var fieldError2 = new FieldError("objectName", "fieldName2", "Error message 2");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        try {
            ReturnError.validateRequest(bindingResult);
            fail("Expected InvalidRequestToManageEntityException");
        } catch (InvalidUserPropertiesException e) {
            String expectedMessage = "Error message 1." + System.lineSeparator() + "Error message 2." + System.lineSeparator();
            assertEquals(expectedMessage, e.getMessage());
        }

    }
}