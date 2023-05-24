package ztu.education.spring_boot_web_project.service;

import ztu.education.spring_boot_web_project.entity.CategoryDish;

import java.util.List;

public interface CategoryDishService {
    List<CategoryDish> getAllCategoriesDish();

    CategoryDish getCategoryDish(int id);

    CategoryDish findByName(String name);

    CategoryDish saveOrUpdateCategoryDish(CategoryDish categoryDish);

    int deleteCategoryDish(int id);
}
