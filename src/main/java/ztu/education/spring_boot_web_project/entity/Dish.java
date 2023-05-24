package ztu.education.spring_boot_web_project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categories_dishes")
    private CategoryDish categoryDish;

    @NotBlank(message = "Назва страви повинна містити до 128 символів")
    @Size(min = 2, max = 128, message = "Назва страви повинна містити хоча б 2 символи, а макс. - 128")
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 65535)
    private String description;

    @NotBlank(message = "Введіть компоненти страви")
    @Column(name = "components", nullable = false, length = 65535)
    private String components;

    @NotNull(message = "Вкажіть ціну страви")
    @Min(value = 0, message = "Страва не може коштувати менше 0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "image_name", length = 256)
    private String imageName;

    public Dish(CategoryDish categoryDish, String name, String description, String components, BigDecimal price) {
        this.categoryDish = categoryDish;
        this.name = name;
        this.description = description;
        this.components = components;
        this.price = price;
    }
}