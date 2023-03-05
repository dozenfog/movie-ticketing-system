package by.issoft.controller;

import by.issoft.domain.user.Role;
import by.issoft.domain.user.User;
import by.issoft.domain.weather.Weather;
import by.issoft.dto.mapper.user.CityMapper;
import by.issoft.dto.mapper.user.UserCategoryMapper;
import by.issoft.dto.out.user.CityOutDTO;
import by.issoft.dto.out.user.UserCategoryOutDTO;
import by.issoft.dto.out.user.UserDetailsOutDTO;
import by.issoft.dto.in.user.UserInDTO;
import by.issoft.dto.in.user.UserUpdateInDTO;
import by.issoft.dto.mapper.user.UserMapper;
import by.issoft.dto.out.user.UserOutDTO;
import by.issoft.exception.NotFoundException;
import by.issoft.service.CityService;
import by.issoft.service.UserCategoryService;
import by.issoft.service.UserService;
import by.issoft.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;
    private final CityService cityService;
    private final UserCategoryService userCategoryService;
    private final UserMapper userMapper;
    private final CityMapper cityMapper;
    private final UserCategoryMapper userCategoryMapper;

    @Operation(summary = "Add a new user")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Added a new user successfully",
                    content = {
                            @Content(schema = @Schema(implementation = UserOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user info was supplied"
            )
    })
    @PostMapping("/signup")
    public UserOutDTO addUser(@Valid @RequestBody UserInDTO userInDTO) {
        User user = userMapper.fromDto(userInDTO);
        user = userService.save(user);
        return userMapper.toDto(user);
    }

    @Operation(summary = "Get my page details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the user",
                    content = {
                            @Content(schema = @Schema(implementation = UserDetailsOutDTO.class))
                    })
    })
    @RolesAllowed({Role.Fields.ADMIN, Role.Fields.USER})
    @GetMapping("/me")
    public UserDetailsOutDTO getMyPageDetails(Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    Weather currentWeather = weatherService.findByCity(user.getCity());
                    return userMapper.toDetailsDto(user, currentWeather);
                })
                .orElseThrow(() -> new NotFoundException("User with username " + principal.getName() + " was not found."));
    }

    @Operation(summary = "Edit my page details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User info was edited",
                    content = {
                            @Content(schema = @Schema(implementation = UserDetailsOutDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user info was supplied"
            )
    })
    @RolesAllowed({Role.Fields.ADMIN, Role.Fields.USER})
    @PutMapping("/me")
    public UserDetailsOutDTO editMyPageDetails(Principal principal,
                                               @Valid @RequestBody UserUpdateInDTO userUpdateInDTO) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    userMapper.fillFromDto(userUpdateInDTO, user);
                    Weather currentWeather = weatherService.findByCity(user.getCity());
                    return userMapper.toDetailsDto(userService.save(user), currentWeather);
                })
                .orElseThrow(() -> new NotFoundException("User with username " + principal.getName() + " was not found."));
    }

    @RolesAllowed({Role.Fields.USER})
    @Operation(summary = "Delete my profile")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted user successfully"
            )
    })
    @DeleteMapping("/me")
    public void deleteMyPage(Principal principal) {
        userService.delete(userService.findByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("User with username " + principal.getName() + " was not found."))
                .getId());
    }

    @Operation(summary = "Get all cities")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all cities",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = CityOutDTO.class)))
                    }
            )
    })
    @GetMapping("/cities")
    public List<CityOutDTO> getAllCities() {
        return cityService.findAll()
                .stream()
                .map(cityMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get all user categories")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found all user categories",
                    content = {
                            @Content(array = @ArraySchema(schema = @Schema(implementation = UserCategoryOutDTO.class)))
                    }
            )
    })
    @GetMapping("/user-categories")
    public List<UserCategoryOutDTO> getAllUserCategories() {
        return userCategoryService.findAll()
                .stream()
                .map(userCategoryMapper::toDto)
                .toList();
    }
}
