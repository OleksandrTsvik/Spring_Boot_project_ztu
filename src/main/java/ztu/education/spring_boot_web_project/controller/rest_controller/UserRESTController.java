package ztu.education.spring_boot_web_project.controller.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ztu.education.spring_boot_web_project.dto.UserLoginDTO;
import ztu.education.spring_boot_web_project.dto.UserRegisterDTO;
import ztu.education.spring_boot_web_project.dto.UserUpdateDTO;
import ztu.education.spring_boot_web_project.entity.User;
import ztu.education.spring_boot_web_project.exception_handling.DataValidationException;
import ztu.education.spring_boot_web_project.exception_handling.NotFoundUserException;
import ztu.education.spring_boot_web_project.exception_handling.ResponseError;
import ztu.education.spring_boot_web_project.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User", description = "Manage site users")
@RestController // контролер, керуючий рест запитами та відповідями
@RequestMapping("/api")
public class UserRESTController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @Operation(
            summary = "Get all users",
            description = "Get a list of all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = User.class))
                    ))
            }
    )
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @Operation(
            summary = "Get user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public User getUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);

        if (user == null) {
            throw new NotFoundUserException("Не вдалося знайти користувача з 'id'=" + id);
        }

        return user;
    }

    @PostMapping("/users/register")
    @Operation(
            summary = "New user registration",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "409", description = "Incorrect data was sent or the data is already in use", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public User register(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        return userService.register(userRegisterDTO);
    }

    @PostMapping("/users/login")
    @Operation(
            summary = "Login for the user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Incorrect data was sent or the user is locked out", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public User login(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        return userService.login(userLoginDTO);
    }

    @PutMapping("/users")
    @Operation(
            summary = "Update user data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Incorrect data was sent or the data is already in use", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public User update(
            @Valid @RequestBody UserUpdateDTO userUpdateDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        return userService.update(userUpdateDTO);
    }

    @DeleteMapping(value = "/users/{id}", produces = "application/json;charset=UTF-8")
    @Operation(
            summary = "Delete user",
            description = "Deleting a user and returning a deadline with the number of deleted records",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    public String delete(@PathVariable("id") int id) {
        return "Кількість видалених записів = " + userService.deleteUser(id);
    }
}
