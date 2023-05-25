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
import ztu.education.spring_boot_web_project.dto.DishSaveDTO;
import ztu.education.spring_boot_web_project.entity.Dish;
import ztu.education.spring_boot_web_project.exception_handling.DataValidationException;
import ztu.education.spring_boot_web_project.exception_handling.NotFoundDishException;
import ztu.education.spring_boot_web_project.exception_handling.ResponseError;
import ztu.education.spring_boot_web_project.exception_handling.SaveDishException;
import ztu.education.spring_boot_web_project.service.DishService;
import ztu.education.spring_boot_web_project.utils.FileManager;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

@Tag(name = "Dish", description = "Manage dishes")
@RestController // контролер, керуючий рест запитами та відповідями
@RequestMapping("/api")
public class DishRESTController {
    @Autowired
    private DishService dishService;

    @GetMapping("/dishes")
    @Operation(
            summary = "Get all dishes",
            description = "Get a list of dishes that can be filtered by name or category id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = Dish.class))
                    ))
            }
    )
    public List<Dish> getAllDishes(
            @RequestParam(value = "dishName", required = false) String dishName,
            @RequestParam(value = "dishCategoryId", required = false) Integer dishCategoryId
    ) {
        if (dishName != null) {
            return dishService.getDishesByName(dishName);
        } else if (dishCategoryId != null) {
            return dishService.getDishesByCategory(dishCategoryId);
        }

        return dishService.getAllDishes();
    }

    @GetMapping("/dishes/{id}")
    @Operation(
            summary = "Get dish by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public Dish getDish(@PathVariable("id") int id) {
        Dish dish = dishService.getDish(id);

        if (dish == null) {
            throw new NotFoundDishException("Не вдалося знайти страву з 'id'=" + id);
        }

        return dish;
    }

    @PostMapping(
            value = "/dishes",
            consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Add a new dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Incorrect data was sent", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "An error occurred while saving the picture", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public Dish add(
            @Valid DishSaveDTO dishSaveDTO, // для обробки multipart/form-data запитів @RequestBody - не працює
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        String pathImagesDir = FileManager.PATH_IMAGES_DIR;

        try {
            return dishService.saveOrUpdateDish(dishSaveDTO, pathImagesDir);
        } catch (IOException e) {
            throw new SaveDishException();
        }
    }

    @PutMapping( // не працює, всі поля параметра dishSaveDTO мають значення - null
            value = "/dishes",
            consumes = {APPLICATION_JSON_VALUE, MULTIPART_FORM_DATA_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    @Operation(
            summary = "Update dish data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Dish not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Incorrect data was sent", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "An error occurred while saving the picture", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public Dish update(
            @Valid DishSaveDTO dishSaveDTO, // для обробки multipart/form-data запитів @RequestBody - не працює
            BindingResult bindingResult
    ) {
        if (dishService.getDish(dishSaveDTO.getId()) == null) {
            throw new NotFoundDishException("Не вдалося знайти страву з 'id'=" + dishSaveDTO.getId());
        }

        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        String pathImagesDir = FileManager.PATH_IMAGES_DIR;

        try {
            return dishService.saveOrUpdateDish(dishSaveDTO, pathImagesDir);
        } catch (IOException e) {
            throw new SaveDishException();
        }
    }

    @DeleteMapping(value = "/dishes/{id}", produces = "application/json;charset=UTF-8")
    @Operation(
            summary = "Delete dish",
            description = "Deleting a dish and returning a message about the result of the deletion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "500", description = "An error occurred while deleting the image", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public String delete(
            @PathVariable("id") int id
    ) {
        String pathImagesDir = FileManager.PATH_IMAGES_DIR;

        try {
            dishService.deleteDish(id, pathImagesDir);
            return "Страву успішно видалено";
        } catch (IOException e) {
            e.printStackTrace();
            return "Під час видалення страви виникла помилка";
        }
    }
}
