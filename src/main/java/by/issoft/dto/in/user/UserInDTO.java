package by.issoft.dto.in.user;

import by.issoft.domain.user.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
public class UserInDTO {
    @NotBlank(message = "First name is mandatory")
    @Size(max = 30, message = "First name is too long")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 30, message = "Last name is too long")
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 60, message = "User name is too long")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email is too long")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{7,20}$", message = "Phone should be valid")
    private String phone;

    @NotBlank
    @Size(max = 100, message = "Password is too long")
    private String password;

    @Size(max = 200, message = "Address is too long")
    private String address;

    @NotNull
    @Positive
    private Long cityId;

    @Past(message = "Birth date is invalid")
    private LocalDate birthDate;

    @NotNull
    @Positive
    private Long userCategoryId;

    @NotNull
    private Role role;
}
