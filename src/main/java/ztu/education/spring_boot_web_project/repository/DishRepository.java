package ztu.education.spring_boot_web_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ztu.education.spring_boot_web_project.entity.Dish;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findDishesByName(String name);

    List<Dish> findDishesByCategoryDishId(int categoryId);

    Dish findDishById(int id);

    int deleteDishById(int id);
}
