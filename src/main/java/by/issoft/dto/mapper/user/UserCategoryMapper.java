package by.issoft.dto.mapper.user;

import by.issoft.domain.user.UserCategory;
import by.issoft.dto.out.user.UserCategoryOutDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserCategoryMapper {
    UserCategoryOutDTO toDto(UserCategory userCategory);
}
