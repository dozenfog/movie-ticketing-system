package by.issoft.controller;

import by.issoft.controller.utils.suppliers.UserSupplier;
import by.issoft.domain.user.Role;
import by.issoft.domain.user.User;
import by.issoft.dto.mapper.user.UserMapper;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static by.issoft.controller.utils.constants.TestConstants.ADMIN_USER_MAPPING;
import static by.issoft.controller.utils.constants.TestConstants.INVALID_ID;
import static by.issoft.controller.utils.constants.TestConstants.PUBLIC_USER_SIGNUP_MAPPING;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = {Role.Fields.ADMIN, Role.Fields.USER})
class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public static final String EXCEPTION_MESSAGE = "User with id " + INVALID_ID + " was not found.";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        //given
        User user = userMapper.fromDto(UserSupplier.getValidUsers().get(0));
        Long returnedId = userRepository.save(user).getId();

        //when & then
        mockMvc.perform(get(ADMIN_USER_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[1].email").value(user.getEmail()));

        userRepository.deleteById(returnedId);
    }

    @Test
    public void testGetUserByValidId() throws Exception {
        //given
        User user = userMapper.fromDto(UserSupplier.getValidUsers().get(1));
        Long id = userRepository.save(user).getId();

        //when & then
        mockMvc.perform(get(ADMIN_USER_MAPPING + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(user.getUserName()));

        userRepository.deleteById(id);
    }

    @Test
    public void testGetUserByInvalidId() throws Exception {
        mockMvc.perform(get(ADMIN_USER_MAPPING + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.devMessage", is(EXCEPTION_MESSAGE)))
                .andExpect(jsonPath("$.userMessage", is(EXCEPTION_MESSAGE)));
    }

    @Test
    public void testAddValidUser() throws Exception {
        //given
        User user = userMapper.fromDto(UserSupplier.getValidUsers().get(2));
        int initSize = (userRepository.findAll()).size();

        final String requestContent = UserSupplier.getValidUserJson();
        //when
        mockMvc.perform(post(PUBLIC_USER_SIGNUP_MAPPING)
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(user.getUserName()));

        //then
        assertEquals((userRepository.findAll()).size(), initSize + 1);
        userRepository.delete(user);
    }
}