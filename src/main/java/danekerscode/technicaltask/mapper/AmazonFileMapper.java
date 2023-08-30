package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.model.AmazonFile;
import danekerscode.technicaltask.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AmazonFileMapper {

    @Mapping(target = "uploadedOn", expression = "java(time)")
    @Mapping(target = "fileName", expression = "java(fileName)")
    @Mapping(target = "owner", expression = "java(owner)")
    AmazonFile toModel(String fileName, User owner, LocalDateTime time);

}
