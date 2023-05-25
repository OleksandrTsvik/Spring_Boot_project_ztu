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
import ztu.education.spring_boot_web_project.entity.CategoryDish;
import ztu.education.spring_boot_web_project.exception_handling.DataValidationException;
import ztu.education.spring_boot_web_project.exception_handling.NotFoundCategoryDishException;
import ztu.education.spring_boot_web_project.exception_handling.ResponseError;
import ztu.education.spring_boot_web_project.service.CategoryDishService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Category Dish", description = "Manage dish categories")
@RestController // контролер, керуючий рест запитами та відповідями
@RequestMapping("/api")
public class CategoryDishRESTController {
    @Autowired
    private CategoryDishService categoryDishService;

    @GetMapping(value = "/categories")
    @Operation(
            summary = "Get all categories",
            description = "Get a list of all product categories",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = CategoryDish.class))
                    ))
            }
    )
    public List<CategoryDish> getAllCategories() {
        return categoryDishService.getAllCategoriesDish();
    }

    @GetMapping(value = "/categories/{id}")
    @Operation(
            summary = "Get category by id",
            description = "Get the category by the specified id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "CategoryDish not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public CategoryDish getCategory(@PathVariable("id") int id) {
        CategoryDish categoryDish = categoryDishService.getCategoryDish(id);

        if (categoryDish == null) {
            throw new NotFoundCategoryDishException("Не вдалося знайти категорію страв з 'id'=" + id);
        }

        return categoryDish;
    }

    @PostMapping("/categories")
    @Operation(
            summary = "Add a new category",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Incorrect data was sent", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public CategoryDish add(
            @Valid @RequestBody CategoryDish categoryDish,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        return categoryDishService.saveOrUpdateCategoryDish(categoryDish);
    }

    @PutMapping("/categories")
    @Operation(
            summary = "Update category data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "CategoryDish not found", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Incorrect data was sent", content = @Content(
                            schema = @Schema(implementation = ResponseError.class)
                    ))
            }
    )
    public CategoryDish update(
            @Valid @RequestBody CategoryDish categoryDish,
            BindingResult bindingResult
    ) {
        if (categoryDishService.getCategoryDish(categoryDish.getId()) == null) {
            throw new NotFoundCategoryDishException("Не вдалося знайти категорію страв з 'id'=" + categoryDish.getId());
        }

        if (bindingResult.hasErrors()) {
            throw new DataValidationException(bindingResult);
        }

        return categoryDishService.saveOrUpdateCategoryDish(categoryDish);
    }

    @DeleteMapping(value = "/categories/{id}", produces = "application/json;charset=UTF-8")
    @Operation(
            summary = "Delete category",
            description = "Deleting a category and returning a deadline with the number of deleted records",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    public String delete(@PathVariable("id") int id) {
        return "Кількість видалених записів = " + categoryDishService.deleteCategoryDish(id);
    }
}
