package ztu.education.spring_boot_web_project.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserUpdateDTO {
    @Min(value = 0, message = "ID не може бути від'ємним")
    private int id;

    @NotBlank(message = "Введіть ПІБ")
    private String fullName;

    @NotBlank(message = "Введіть номер телефону")
    @Pattern(regexp = "^380\\d{9}$", message = "Номер телефону має містити 12 цифр та починатися з \"380\"")
    private String phoneNumber;

    @NotBlank(message = "Введіть електронну пошту")
    @Size(max = 64, message = "Назва страви повинна містити не більше 64 символів")
    @Email(message = "Некоректна електронна адреса")
    private String email;

    @NotBlank(message = "Введіть пароль")
    @Size(min = 6, max = 32, message = "Довжина пароля має бути від 6 до 32 символів")
    private String password;
}
