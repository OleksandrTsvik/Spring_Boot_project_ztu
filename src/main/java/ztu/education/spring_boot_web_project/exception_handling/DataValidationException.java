package ztu.education.spring_boot_web_project.exception_handling;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataValidationException extends RuntimeException {
    protected BindingResult bindingResult;

    public DataValidationException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }

    public Map<String, List<String>> getObjectErrors() {
        List<FieldError> errors = this.bindingResult.getFieldErrors();
        Map<String, List<String>> mapErrors = new HashMap<>();

        for (FieldError error : errors) {
            String key = error.getField();

            List<String> arrErrors = mapErrors.get(key);
            if (arrErrors == null) {
                arrErrors = new ArrayList<>();
            }

            arrErrors.add(error.getDefaultMessage());
            mapErrors.put(key, arrErrors);
        }

        return mapErrors;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}