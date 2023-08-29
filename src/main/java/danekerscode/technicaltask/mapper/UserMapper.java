package danekerscode.technicaltask.mapper;

import danekerscode.technicaltask.dto.UserDTO;
import danekerscode.technicaltask.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;

@Mapper(
        imports = ArrayList.class
)
public interface UserMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "files" , expression = "java(new ArrayList<>())")
    User toModel(UserDTO dto);
}
