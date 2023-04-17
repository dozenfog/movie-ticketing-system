package by.issoft.dto.out.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
