package ztu.education.spring_boot_web_project.exception_handling;

// Клас виняток при неможливості отримати користувача з бази
public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String message) {
        super(message);
    }
}