package ztu.education.spring_boot_web_project.seed;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztu.education.spring_boot_web_project.entity.CategoryDish;
import ztu.education.spring_boot_web_project.entity.Dish;
import ztu.education.spring_boot_web_project.service.CategoryDishService;
import ztu.education.spring_boot_web_project.service.DishService;

import java.math.BigDecimal;

@Component
public class DishesSeed implements InitializingBean {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryDishService categoryDishService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dishService.getCountDishes() == 0) {
            String categoryDishName = "Гастрономічні шедеври";
            CategoryDish categoryDish = categoryDishService.findByName(categoryDishName);

            if (categoryDish == null) {
                categoryDish = new CategoryDish();
                categoryDish.setName(categoryDishName);

                categoryDish = categoryDishService.saveOrUpdateCategoryDish(categoryDish);
            }

            Dish[] dishes = new Dish[]{
                    new Dish(categoryDish, "Паштет з гусиних печінок з малиновим соусом", "Паштет з гусиних печінок на тостовому хлібі з малиновим соусом", "гусина печінка, тостовий хліб, малиновий соус", new BigDecimal("425")),
                    new Dish(categoryDish, "Філе з лосося на грилі зі свіжими овочами і соусом Бернез", "Філе з лосося на грилі зі свіжими овочами та соусом Бернез", "лосось, овочі, соус Бернез", new BigDecimal("625")),
                    new Dish(categoryDish, "Форель на грилі з капустою-броколі та картоплею від шеф-кухаря", "Форель на грилі з капустою-броколі та картоплею від шеф-кухаря", "форель, капуста-броколі, картопля", new BigDecimal("495")),
                    new Dish(categoryDish, "Консоме з морепродуктів з рисом та лимоном", "Консоме з морепродуктів з рисом та лимоном", "морепродукти, рис, лимон", new BigDecimal("345")),
                    new Dish(categoryDish, "Салат зі свіжих овочів з авокадо та тигровими креветками у соусі 'Цезар'", "Салат зі свіжих овочів з авокадо та тигровими креветками у соусі 'Цезар'", "овочі, авокадо, креветки, соус 'Цезар'", new BigDecimal("415")),
                    new Dish(categoryDish, "Шинка з ялівцем та медом, подається з грильованим хлібом", "Шинка з ялівцем та медом, подається з грильованим хлібом", "шинка, ялівець, мед, грильований хліб", new BigDecimal("355")),
                    new Dish(categoryDish, "Різотто з грибами та білим вином з додаванням трюфельного масла", "Різотто з грибами та білим вином з додаванням трюфельного масла", "рис, гриби, біле вино, трюфельне масло", new BigDecimal("465")),
                    new Dish(categoryDish, "Карпаччо з м'яса телятини з пармезаном та руколою", "Карпаччо з м'яса телятини з пармезаном та руколою", "телятина, пармезан, рукола", new BigDecimal("485")),
                    new Dish(categoryDish, "Спагетті карбонара з пекучим перцем та пеконом", "Спагетті карбонара з пекучим перцем та пеконом", "спагетті, яйця, пекінг, перець", new BigDecimal("375")),
                    new Dish(categoryDish, "Медовик з кремом з маскарпоне та свіжими ягодами", "Медовик з кремом з маскарпоне та свіжими ягодами", "медовик, маскарпоне, ягоди", new BigDecimal("265"))
            };

            for (Dish dish : dishes) {
                dishService.saveOrUpdateDish(dish);
            }
        }
    }
}
