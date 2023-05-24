package ztu.education.spring_boot_web_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ztu.education.spring_boot_web_project.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminById(int id);

    Admin findAdminByEmail(String email);

    int deleteAdminById(int id);
}
