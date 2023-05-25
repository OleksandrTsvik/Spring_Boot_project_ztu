package ztu.education.spring_boot_web_project.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@ControllerAdvice // Анотація ControllerAdvice надає класу функціональність Global Exception Handler'a
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(NotFoundUserException exception) {
        return new ResponseEntity<>(
                new ResponseError(exception.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(NotFoundDishException exception) {
        return new ResponseEntity<>(
                new ResponseError(exception.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(SaveDishException exception) {
        return new ResponseEntity<>(
                new ResponseError(exception.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleException(DataValidationException exception) {
        return new ResponseEntity<>(
                exception.getObjectErrors(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(ResponseStatusException exception) {
        return new ResponseEntity<>(
                new ResponseError(exception.getReason(), exception.getStatus().value()),
                exception.getStatus()
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseError> handleException(Exception exception) {
        return new ResponseEntity<>(
                new ResponseError(exception.getMessage(), 400),
                HttpStatus.BAD_REQUEST
        );
    }
}