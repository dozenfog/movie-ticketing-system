package by.issoft.controller.utils.suppliers;

import by.issoft.domain.user.Role;
import by.issoft.dto.in.user.UserInDTO;
import by.issoft.dto.in.user.UserUpdateInDTO;

import java.time.LocalDate;
import java.util.List;

public class UserSupplier {
    public static String getValidUserJson() {
        return "{\"id\":null,\"firstName\":\"HHH\",\"lastName\":\"fwlcdj\",\"userName\":" +
                "\"fjqeow\",\"email\":\"lfuhdszvl@rfu.com\",\"phone\":\"+46726425765\",\"password\":\"kvqwe;aroje\"," +
                "\"cityId\":1,\"registrationDateTime\":\"2012-12-23T12:34:29\",\"userCategoryId\":2,\"role\":0}";
    }

    public static List<UserInDTO> getValidUsers() {
        return List.of(UserInDTO.builder()
                        .firstName("A")
                        .lastName("B")
                        .userName("C")
                        .email("D@rfu.com")
                        .phone("+46726425765")
                        .password("nfskldjfnht4938up")
                        .cityId(1L)
                        .userCategoryId(1L)
                        .role(Role.USER)
                        .build(),
                UserInDTO.builder()
                        .firstName("GG")
                        .lastName("BJJ")
                        .userName("Cfhiwle")
                        .email("gvwelr@rfu.com")
                        .phone("+753024542389")
                        .password("fq;lwerj;oware")
                        .cityId(1L)
                        .userCategoryId(1L)
                        .role(Role.USER)
                        .build(),
                UserInDTO.builder()
                        .firstName("HHH")
                        .lastName("fwlcdj")
                        .userName("fjqeow")
                        .email("lfuhdszvl@rfu.com")
                        .phone("+46726425765")
                        .password("kvqwe;aroje")
                        .cityId(1L)
                        .userCategoryId(2L)
                        .role(Role.ADMIN)
                        .build(),
                UserInDTO.builder()
                        .firstName("JJJJJ")
                        .lastName("gjveowr")
                        .userName("veafls")
                        .email("cjqwoe@rfu.com")
                        .phone("+28547835874")
                        .password("cvq;wrhnfwiun")
                        .cityId(1L)
                        .userCategoryId(3L)
                        .role(Role.USER)
                        .build(),
                UserInDTO.builder()
                        .firstName("AAA")
                        .lastName("ASB")
                        .userName("CSSS")
                        .email("GGGGG@rfu.com")
                        .phone("+46238746937")
                        .password("rhf29304htf0825")
                        .cityId(1L)
                        .userCategoryId(1L)
                        .role(Role.USER)
                        .build());
    }

    public static UserInDTO getUserNoFirstName() {
        return UserInDTO.builder()
                .firstName(null)
                .lastName("Garfield")
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserEmptyFirstName() {
        return UserInDTO.builder()
                .firstName("         ")
                .lastName("Garfield")
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoLastName() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName(null)
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserEmptyLastName() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("          ")
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoUserName() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName(null)
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserEmptyUserName() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("         ")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserInvalidEmail() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoEmail() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email(null)
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserInvalidPhone() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("vwlcnskd@gmail.com")
                .phone("+2")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoPhone() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("vwlcnskd@gmail.com")
                .phone(null)
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoPassword() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("vwlcnskd@gmail.com")
                .phone("+2333333333333")
                .password(null)
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserEmptyPassword() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("vwlcnskd@gmail.com")
                .phone("+2333333333333")
                .cityId(1L)
                .password("")
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserPasswordTooLong() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("Garfield")
                .userName("andygar")
                .email("vwlcnskd@gmail.com")
                .phone("+2333333333333")
                .password("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaa")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserInvalidBirthDate() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("S")
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .birthDate(LocalDate.MAX)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }

    public static UserInDTO getUserNoRole() {
        return UserInDTO.builder()
                .firstName("A")
                .lastName("S")
                .userName("andygar")
                .email("velirjhn5fvw@rfu.com")
                .phone("+2568498644")
                .password("rb3947x0gx8tfhy57ur8h0y84th")
                .cityId(1L)
                .userCategoryId(3L)
                .role(null)
                .build();
    }

    public static UserUpdateInDTO getUpdateUserInvalidEmail() {
        UserUpdateInDTO userUpdateInDTO = new UserUpdateInDTO();
        userUpdateInDTO.setEmail("vnlsakdjrnfk");
        return userUpdateInDTO;
    }

    public static UserUpdateInDTO getUpdateUserInvalidPhone() {
        UserUpdateInDTO userUpdateInDTO = new UserUpdateInDTO();
        userUpdateInDTO.setPhone("44");
        return userUpdateInDTO;
    }

    public static UserUpdateInDTO getUpdateUserInvalidPassword() {
        UserUpdateInDTO userUpdateInDTO = new UserUpdateInDTO();
        userUpdateInDTO.setPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaa");
        return userUpdateInDTO;
    }

    public static UserUpdateInDTO getUpdateUserPayload() {
        UserUpdateInDTO userUpdateInDTO = new UserUpdateInDTO();
        userUpdateInDTO.setFirstName("New");
        userUpdateInDTO.setLastName("New");
        userUpdateInDTO.setEmail("New@new");
        userUpdateInDTO.setPhone("+11111111");
        return userUpdateInDTO;
    }

    public static UserInDTO getUpdatedUser() {
        return UserInDTO.builder()
                .firstName("New")
                .lastName("New")
                .userName("CSSS")
                .email("New@new")
                .phone("+11111111")
                .password("nfskldjfnht4938up")
                .cityId(1L)
                .userCategoryId(1L)
                .role(Role.USER)
                .build();
    }
}
