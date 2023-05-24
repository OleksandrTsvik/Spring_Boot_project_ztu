package ztu.education.spring_boot_web_project.service;

import ztu.education.spring_boot_web_project.dto.AdminSaveDTO;
import ztu.education.spring_boot_web_project.entity.Admin;

import java.util.List;

public interface AdminService {
    AdminSaveDTO getAdminSaveDTO(int id);

    Admin findByEmail(String email);

    Admin register(AdminSaveDTO adminSaveDTO);

    Admin login(Admin admin);

    Admin login(String email, String password);

    List<Admin> getAllAdmins();

    int deleteAdmin(int id);
}
