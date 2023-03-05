package by.issoft.dto.out.user;

import by.issoft.domain.user.Role;
import by.issoft.domain.user.UserCategory;
import by.issoft.dto.out.weather.WeatherOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsOutDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private CityOutDTO city;
    private LocalDateTime registrationDateTime;
    private UserCategory userCategory;
    private Role role;
    private WeatherOutDTO weather;
}
