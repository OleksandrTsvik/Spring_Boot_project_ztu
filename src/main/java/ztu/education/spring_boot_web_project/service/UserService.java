package ztu.education.spring_boot_web_project.service;

import ztu.education.spring_boot_web_project.dto.UserLoginDTO;
import ztu.education.spring_boot_web_project.dto.UserRegisterDTO;
import ztu.education.spring_boot_web_project.entity.User;

import java.util.List;

public interface UserService {
    User register(UserRegisterDTO user);

    User login(UserLoginDTO userLoginDTO);

    User login(String email, String password);

    List<User> getAllUsers();

    int deleteUser(int id);

    String toggleUser(int id);
}
