package by.issoft.dto.in.cinema;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Builder
public class CinemaInDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name is too long")
    private String name;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 300, message = "Address is too long")
    private String address;

    @NotNull
    @Positive
    private Long cityId;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 60, message = "Email is too long")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?\\d{7,20}$", message = "Phone should be valid")
    private String phone;

    @NotNull
    @NotEmpty
    private List<MovieRoomInDTO> movieRooms;
}
