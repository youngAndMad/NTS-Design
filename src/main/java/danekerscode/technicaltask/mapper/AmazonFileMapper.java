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
    @Mapping(target = "owner", expression = "java(owner)")
    @Mapping(target = "fileName" , expression = "java(formatFileName(fileName))")
    @Mapping(target = "id" , ignore = true)
    AmazonFile toModel(String fileName, User owner, LocalDateTime time);

    default String formatFileName(String s){
        return s.replace('T' , ' ');
    }

}
