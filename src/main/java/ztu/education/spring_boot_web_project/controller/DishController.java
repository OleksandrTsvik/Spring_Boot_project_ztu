package ztu.education.spring_boot_web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.DishSaveDTO;
import ztu.education.spring_boot_web_project.entity.Dish;
import ztu.education.spring_boot_web_project.service.CategoryDishService;
import ztu.education.spring_boot_web_project.service.DishService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryDishService categoryDishService;

    @RequestMapping("/dish/{id}")
    public String dish(@PathVariable("id") int id, Model model) {
        Dish dish = dishService.getDish(id);

        if (dish == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("dish", dish);

        return "dish";
    }

    @RequestMapping("/dishes")
    public String dishes(
            @RequestParam(value = "dishName", required = false) String dishName,
            @RequestParam(value = "dishCategoryId", required = false) Integer dishCategoryId,
            Model model
    ) {
        if (dishName != null) {
            model.addAttribute("searchByDishName", dishName);
            model.addAttribute("dishes", dishService.getDishesByName(dishName));
        } else if (dishCategoryId != null) {
            model.addAttribute("searchByDishCategory", categoryDishService.getCategoryDish(dishCategoryId));
            model.addAttribute("dishes", dishService.getDishesByCategory(dishCategoryId));
        } else {
            model.addAttribute("dishes", dishService.getAllDishes());
        }

        return "listDishes";
    }

    @RequestMapping(value = "/admin/dish", method = RequestMethod.GET)
    public String saveDish(Model model) {
        model.addAttribute("dish", new DishSaveDTO());
        model.addAttribute("categoriesDish", categoryDishService.getAllCategoriesDish());

        return "formDish";
    }

    @RequestMapping(value = "/admin/dish", method = RequestMethod.POST)
    public String saveDish(
            @Valid @ModelAttribute("dish") DishSaveDTO dish,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            String pathImagesDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";

            try {
                dishService.saveOrUpdateDish(dish, pathImagesDir);
                model.addAttribute("successMessage", "Страву успішно збережено");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("dangerMessage", "Під час збереження страви виникла помилка");
            }

            model.addAttribute("dish", new DishSaveDTO());
        } else {
            model.addAttribute("dish", dish);
        }

        model.addAttribute("categoriesDish", categoryDishService.getAllCategoriesDish());

        return "formDish";
    }

    @RequestMapping(value = "/admin/dish/edit/{id}", method = RequestMethod.GET)
    public String editDish(@PathVariable("id") int id, Model model) {
        model.addAttribute("dish", dishService.getDishSaveDTO(id));
        model.addAttribute("categoriesDish", categoryDishService.getAllCategoriesDish());

        return "formDish";
    }

    @RequestMapping(value = "/admin/dish/delete/{id}", method = RequestMethod.GET)
    public String deleteDish(
            @PathVariable("id") int id,
            Model model
    ) {
        String pathImagesDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";

        try {
            dishService.deleteDish(id, pathImagesDir);
            model.addAttribute("deleteMessage", "Страву успішно видалено");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("deleteMessage", "Під час видалення страви виникла помилка");
        }

        model.addAttribute("dishes", dishService.getAllDishes());

        return "listDishes";
    }
}
