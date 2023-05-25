package ztu.education.spring_boot_web_project.exception_handling;

public class NotFoundDishException extends RuntimeException {
    public NotFoundDishException(String message) {
        super(message);
    }
}