package ztu.education.spring_boot_web_project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ztu.education.spring_boot_web_project.entity.CategoryDish;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class DishSaveDTO {
    private int id;

    private CategoryDish categoryDish;

    @NotBlank(message = "Назва страви повинна містити до 128 символів")
    @Size(min = 2, message = "Назва страви повинна містити хоча б 2 символи")
    private String name;

    private String description;

    @NotBlank(message = "Введіть компоненти страви")
    private String components;

    @NotNull(message = "Вкажіть ціну страви")
    @Min(value = 0, message = "Страва не може коштувати менше 0")
    private BigDecimal price;

    private MultipartFile image;
}
