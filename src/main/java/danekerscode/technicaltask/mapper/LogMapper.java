package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.model.Log;
import danekerscode.technicaltask.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface LogMapper {

    @Mapping(target = "owner", expression = "java(owner)")
    @Mapping(target = "content", expression = "java(content)")
    @Mapping(target = "id", ignore = true)
    Log toModel(String content, User owner);
}
