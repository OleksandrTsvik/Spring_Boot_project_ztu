package ztu.education.spring_boot_web_project.service;

import ztu.education.spring_boot_web_project.dto.DishSaveDTO;
import ztu.education.spring_boot_web_project.entity.Dish;

import java.io.IOException;
import java.util.List;

public interface DishService {
    Long getCountDishes();

    List<Dish> getAllDishes();

    List<Dish> getDishesByName(String name);

    List<Dish> getDishesByCategory(Integer categoryId);

    Dish getDish(int id);

    DishSaveDTO getDishSaveDTO(int id);

    Dish saveOrUpdateDish(DishSaveDTO dishDTO, String pathImagesDir) throws IOException;

    Dish saveOrUpdateDish(Dish dish);

    void deleteDish(int id, String pathImagesDir) throws IOException;
}
