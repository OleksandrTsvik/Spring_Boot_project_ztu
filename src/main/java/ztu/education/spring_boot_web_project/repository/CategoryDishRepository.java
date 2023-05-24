package ztu.education.spring_boot_web_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ztu.education.spring_boot_web_project.entity.CategoryDish;

public interface CategoryDishRepository extends JpaRepository<CategoryDish, Integer> {
    CategoryDish findCategoryDishById(int id);

    CategoryDish findCategoryDishByName(String name);

    int deleteCategoryDishById(int id);
}
