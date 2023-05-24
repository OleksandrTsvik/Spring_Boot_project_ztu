package ztu.education.spring_boot_web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.entity.CategoryDish;
import ztu.education.spring_boot_web_project.service.CategoryDishService;

import javax.validation.Valid;

@Controller
public class CategoryDishController {
    @Autowired
    private CategoryDishService categoryDishService;

    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public String categories(Model model) {
        model.addAttribute("categories", categoryDishService.getAllCategoriesDish());

        return "listCategories";
    }

    @RequestMapping(value = "/admin/category", method = RequestMethod.GET)
    public String saveCategory(Model model) {
        model.addAttribute("category", new CategoryDish());

        return "formCategoryDish";
    }

    @RequestMapping(value = "/admin/category", method = RequestMethod.POST)
    public String saveCategory(
            @Valid @ModelAttribute("category") CategoryDish categoryDish,
            BindingResult bindingResult,
            Model model
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                categoryDishService.saveOrUpdateCategoryDish(categoryDish);
                model.addAttribute("successMessage", "Категорію успішно збережено");
                model.addAttribute("category", new CategoryDish());
            } catch (ResponseStatusException e) {
                e.printStackTrace();
                model.addAttribute("dangerMessage", e.getReason());
                model.addAttribute("category", categoryDish);
            }
        } else {
            model.addAttribute("category", categoryDish);
        }

        return "formCategoryDish";
    }

    @RequestMapping(value = "/admin/category/edit/{id}", method = RequestMethod.GET)
    public String editDish(@PathVariable("id") int id, Model model) {
        CategoryDish categoryDish = categoryDishService.getCategoryDish(id);

        if (categoryDish == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("category", categoryDish);

        return "formCategoryDish";
    }

    @RequestMapping(value = "/admin/category/delete/{id}", method = RequestMethod.GET)
    public String deleteDish(@PathVariable("id") int id, Model model) {
        model.addAttribute("deleteMessage", "Кількість видалених записів = " +
                categoryDishService.deleteCategoryDish(id));
        model.addAttribute("categories", categoryDishService.getAllCategoriesDish());

        return "listCategories";
    }
}
