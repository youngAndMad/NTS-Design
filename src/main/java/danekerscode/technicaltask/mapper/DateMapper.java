package danekerscode.technicaltask.mapper;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateMapper {

    public static LocalDateTime convertToDateTime(String input) {
        var formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

        return LocalDateTime
                .parse(
                        input,
                        formatter
                );
    }

}
