package ztu.education.spring_boot_web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ztu.education.spring_boot_web_project.service.CategoryDishService;

@Controller
public class HomeController {
    @Autowired
    private CategoryDishService categoryDishService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryDishService.getAllCategoriesDish());

        return "index";
    }
}
