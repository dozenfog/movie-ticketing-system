package by.issoft.dto.mapper.user;

import by.issoft.domain.user.User;
import by.issoft.domain.weather.Weather;
import by.issoft.dto.out.user.UserDetailsOutDTO;
import by.issoft.dto.in.user.UserInDTO;
import by.issoft.dto.in.user.UserUpdateInDTO;
import by.issoft.dto.out.user.UserOutDTO;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface UserMapper {
    @Mapping(source = "userCategoryId", target = "userCategory.id")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(source = "cityId", target = "city.id")
    User fromDto(UserInDTO userInDTO);

    UserOutDTO toDto(User user);

    @Mapping(source = "user.city", target = "city")
    @Mapping(source = "user.id", target = "id")
    UserDetailsOutDTO toDetailsDto(User user, Weather weather);

    @Mapping(target = "password", qualifiedByName = "encodePassword")
    void fillFromDto(UserUpdateInDTO userUpdateInDTO, @MappingTarget User user);
}
