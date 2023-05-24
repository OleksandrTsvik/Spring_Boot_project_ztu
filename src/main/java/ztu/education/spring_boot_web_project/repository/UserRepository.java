package ztu.education.spring_boot_web_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ztu.education.spring_boot_web_project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);

    User findUserByEmail(String email);

    User findUserByPhoneNumber(String phoneNumber);

    int deleteUserById(int id);
}
