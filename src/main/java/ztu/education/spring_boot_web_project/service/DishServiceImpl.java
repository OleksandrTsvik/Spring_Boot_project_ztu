package ztu.education.spring_boot_web_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.DishSaveDTO;
import ztu.education.spring_boot_web_project.entity.Dish;
import ztu.education.spring_boot_web_project.repository.DishRepository;
import ztu.education.spring_boot_web_project.utils.FileManager;

import java.io.IOException;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository dishRepository;

    @Override
    @Transactional
    public Long getCountDishes() {
        return dishRepository.count();
    }

    @Override
    @Transactional
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    @Transactional
    public List<Dish> getDishesByName(String name) {
        return dishRepository.findDishesByName(name);
    }

    @Override
    @Transactional
    public List<Dish> getDishesByCategory(Integer categoryId) {
        return dishRepository.findDishesByCategoryDishId(categoryId);
    }

    @Override
    @Transactional
    public Dish getDish(int id) {
        return dishRepository.findDishById(id);
    }

    @Override
    @Transactional
    public DishSaveDTO getDishSaveDTO(int id) {
        Dish dish = this.getDish(id);

        if (dish == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        DishSaveDTO dishSaveDTO = new DishSaveDTO();

        dishSaveDTO.setId(dish.getId());
        dishSaveDTO.setName(dish.getName());
        dishSaveDTO.setDescription(dish.getDescription());
        dishSaveDTO.setComponents(dish.getComponents());
        dishSaveDTO.setPrice(dish.getPrice());
        dishSaveDTO.setCategoryDish(dish.getCategoryDish());

        return dishSaveDTO;
    }

    @Override
    @Transactional
    public Dish saveOrUpdateDish(DishSaveDTO dishDTO, String pathImagesDir) throws IOException {
        String imageName = FileManager.uploadFile(dishDTO.getImage(), pathImagesDir);

        Dish dish = new Dish();

        dish.setId(dishDTO.getId());
        dish.setCategoryDish(dishDTO.getCategoryDish());
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setComponents(dishDTO.getComponents());
        dish.setPrice(dishDTO.getPrice());
        dish.setImageName(imageName);

        return dishRepository.save(dish);
    }

    @Override
    @Transactional
    public Dish saveOrUpdateDish(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    @Transactional
    public void deleteDish(int id, String pathImagesDir) throws IOException {
        Dish dish = this.getDish(id);

        if (dish == null) {
            return;
        }

        if (dish.getImageName() != null && !dish.getImageName().isEmpty()) {
            FileManager.deleteFile(pathImagesDir + "\\" + dish.getImageName());
        }

        dishRepository.delete(dish);
    }
}
