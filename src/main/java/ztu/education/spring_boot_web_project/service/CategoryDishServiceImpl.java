package ztu.education.spring_boot_web_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.entity.CategoryDish;
import ztu.education.spring_boot_web_project.repository.CategoryDishRepository;

import java.util.List;

@Service
public class CategoryDishServiceImpl implements CategoryDishService {
    @Autowired
    private CategoryDishRepository categoryDishRepository;

    @Override
    @Transactional
    public List<CategoryDish> getAllCategoriesDish() {
        return categoryDishRepository.findAll();
    }

    @Override
    @Transactional
    public CategoryDish getCategoryDish(int id) {
        return categoryDishRepository.findCategoryDishById(id);
    }

    @Override
    @Transactional
    public CategoryDish findByName(String name) {
        return categoryDishRepository.findCategoryDishByName(name);
    }

    @Override
    @Transactional
    public CategoryDish saveOrUpdateCategoryDish(CategoryDish categoryDish) {
        if (this.findByName(categoryDish.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Введений каталог уже існує");
        }

        return categoryDishRepository.save(categoryDish);
    }

    @Override
    @Transactional
    public int deleteCategoryDish(int id) {
        return categoryDishRepository.deleteCategoryDishById(id);
    }
}
