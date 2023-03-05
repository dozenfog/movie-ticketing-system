package by.issoft.validation;

import by.issoft.controller.utils.suppliers.UserSupplier;
import by.issoft.domain.user.Role;
import by.issoft.domain.user.User;
import by.issoft.dto.in.user.UserInDTO;
import by.issoft.dto.in.user.UserUpdateInDTO;
import by.issoft.dto.mapper.user.UserMapper;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.function.Supplier;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_USER_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.ID;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.PUBLIC_USER_SIGNUP_MAPPING;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {Role.Fields.ADMIN, Role.Fields.USER})
public class UserValidationTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testAddInvalidUserNoFirstName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoFirstName, "firstName", "First name is mandatory");
    }

    @Test
    public void testAddInvalidUserEmptyFirstName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserEmptyFirstName, "firstName", "First name is mandatory");
    }

    @Test
    public void testAddInvalidUserNoLastName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoLastName, "lastName", "Last name is mandatory");
    }

    @Test
    public void testAddInvalidUserEmptyLastName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserEmptyLastName, "lastName", "Last name is mandatory");
    }

    @Test
    public void testAddInvalidUserNoUserName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoUserName, "userName", "Username is mandatory");
    }

    @Test
    public void testAddInvalidUserEmptyUserName() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserEmptyUserName, "userName", "Username is mandatory");
    }

    @Test
    public void testAddInvalidUserInvalidEmail() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserInvalidEmail, "email", "Email should be valid");
    }

    @Test
    public void testAddInvalidUserNoEmail() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoEmail, "email", "Email is required");
    }

    @Test
    public void testAddInvalidUserInvalidPhone() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserInvalidPhone, "phone", "Phone should be valid");
    }

    @Test
    public void testAddInvalidUserNoPhone() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoPhone, "phone", "Phone is required");
    }

    @Test
    public void testAddInvalidUserNoPasswordHash() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoPassword, "password", "must not be blank");
    }

    @Test
    public void testAddInvalidUserEmptyPasswordHash() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserEmptyPassword, "password", "must not be blank");
    }

    @Test
    public void testAddInvalidUserPasswordHashTooLong() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserPasswordTooLong, "password",
                "Password is too long");
    }

    @Test
    public void testAddUserInvalidBirthDate() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserInvalidBirthDate, "birthDate",
                "Birth date is invalid");
    }

    @Test
    public void testAddUserNoRole() throws Exception {
        testAddInvalidPayload(UserSupplier::getUserNoRole, "role",
                "must not be null");
    }

    @Test
    public void testDeleteUserByValidId() throws Exception {
        //given
        User user = userMapper.fromDto(UserSupplier.getValidUsers().get(3));
        Long id = userRepository.save(user).getId();

        //when & then
        mockMvc.perform(delete(ADMIN_USER_MAPPING + id))
                .andExpect(status().isOk());

        mockMvc.perform(get(ADMIN_USER_MAPPING + id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUserByInvalidId() throws Exception {
        mockMvc.perform(put(ADMIN_USER_MAPPING + INVALID_ID)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(getExceptionMessage())))
                .andExpect(jsonPath("$.userMessage", is(getExceptionMessage())));
    }

    @Test
    public void testUpdateUserByValidIdInvalidEmail() throws Exception {
        testUpdateInvalidPayload(UserSupplier::getUpdateUserInvalidEmail, "email", "Email should be valid");
    }

    @Test
    public void testUpdateUserByValidIdInvalidPhone() throws Exception {
        testUpdateInvalidPayload(UserSupplier::getUpdateUserInvalidPhone, "phone", "Phone should be valid");
    }

    @Test
    public void testUpdateUserByValidIdInvalidPassword() throws Exception {
        testUpdateInvalidPayload(UserSupplier::getUpdateUserInvalidPassword, "password",
                "size must be between 0 and 100");
    }

    @Test
    public void testUpdateUser() throws Exception {
        //given
        User user = userMapper.fromDto(UserSupplier.getValidUsers().get(4));
        Long id = userRepository.save(user).getId();

        UserUpdateInDTO updateUserPayload = UserSupplier.getUpdateUserPayload();
        final String requestContent = objectMapper.writeValueAsString(updateUserPayload);
        User updatedUser = userMapper.fromDto(UserSupplier.getUpdatedUser());

        //when & then
        mockMvc.perform(put(ADMIN_USER_MAPPING + id)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));

        userRepository.deleteById(id);
    }

    private void testAddInvalidPayload(Supplier<UserInDTO> userSupplier, String jsonFieldPath, String devMessage)
            throws Exception {
        UserInDTO user = userSupplier.get();
        final String requestContent = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(PUBLIC_USER_SIGNUP_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$." + jsonFieldPath + ".devMessage", is(devMessage)))
                .andExpect(jsonPath("$." + jsonFieldPath + ".userMessage").value(Matchers.endsWith("is not valid")));
    }

    private void testUpdateInvalidPayload(Supplier<UserUpdateInDTO> userSupplier, String jsonFieldPath, String devMessage)
            throws Exception {
        UserUpdateInDTO user = userSupplier.get();
        final String requestContent = objectMapper.writeValueAsString(user);

        mockMvc.perform(put(ADMIN_USER_MAPPING + ID)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$." + jsonFieldPath + ".devMessage", is(devMessage)))
                .andExpect(jsonPath("$." + jsonFieldPath + ".userMessage").value(Matchers.endsWith("is not valid")));
    }

    private String getExceptionMessage() {
        return "User with id " + INVALID_ID + " was not found.";
    }
}
