package ztu.education.spring_boot_web_project.utils;

public class NavBar {
    public String link;
    public String label;

    public NavBar(String link, String label) {
        this.link = link;
        this.label = label;
    }

    public static NavBar[] getAdminNavBar() {
        return new NavBar[]{
                new NavBar("/dishes", "Страви"),
                new NavBar("/admin/dish", "Додати страву"),
                new NavBar("/admin/categories", "Категорії"),
                new NavBar("/admin/category", "Додати категорію"),
                new NavBar("/admin/users", "Користувачі"),
                new NavBar("/admin/orders", "Замовлення"),
                new NavBar("/admin/admins", "Адміністратори"),
                new NavBar("/admin/add", "Додати адміністратора"),
                new NavBar("/swagger-ui/index.html#/", "Swagger")
        };
    }

    public String getLink() {
        return link;
    }

    public String getLabel() {
        return label;
    }
}
