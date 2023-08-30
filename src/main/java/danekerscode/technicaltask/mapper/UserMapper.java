package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;

@Mapper(
        imports = ArrayList.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "files" , expression = "java(new ArrayList<>())")
    User toModel(UserDTO dto);
}
