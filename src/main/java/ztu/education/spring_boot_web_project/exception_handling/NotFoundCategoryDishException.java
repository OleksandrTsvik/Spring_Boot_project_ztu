package ztu.education.spring_boot_web_project.exception_handling;

public class NotFoundCategoryDishException extends RuntimeException {
    public NotFoundCategoryDishException(String message) {
        super(message);
    }
}