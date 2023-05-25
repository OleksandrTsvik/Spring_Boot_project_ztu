package ztu.education.spring_boot_web_project.exception_handling;

public class SaveDishException extends RuntimeException {
    public SaveDishException() {
        super("Під час збереження страви виникла помилка");
    }

    public SaveDishException(String message) {
        super(message);
    }
}
