package ztu.education.spring_boot_web_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.AdminSaveDTO;
import ztu.education.spring_boot_web_project.entity.Admin;
import ztu.education.spring_boot_web_project.repository.AdminRepository;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public AdminSaveDTO getAdminSaveDTO(int id) {
        Admin admin = adminRepository.findAdminById(id);

        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        AdminSaveDTO adminSaveDTO = new AdminSaveDTO();

        adminSaveDTO.setId(admin.getId());
        adminSaveDTO.setEmail(admin.getEmail());

        return adminSaveDTO;
    }

    @Override
    @Transactional
    public Admin findByEmail(String email) {
        return adminRepository.findAdminByEmail(email);
    }

    @Override
    @Transactional
    public Admin register(AdminSaveDTO adminSaveDTO) {
        Admin adminById = null;
        if (adminSaveDTO.getId() != null) {
            adminById = adminRepository.findAdminById(adminSaveDTO.getId());
        }

        if (
                (adminById != null && !adminById.getEmail().equals(adminSaveDTO.getEmail())) &&
                        adminRepository.findAdminByEmail(adminSaveDTO.getEmail()) != null
        ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Електронна адреса уже використовується");
        }

        Admin newAdmin = new Admin();

        newAdmin.setId(adminSaveDTO.getId());
        newAdmin.setEmail(adminSaveDTO.getEmail());
        newAdmin.setPassword(bCryptPasswordEncoder.encode(adminSaveDTO.getPassword()));

        return adminRepository.save(newAdmin);
    }

    @Override
    @Transactional
    public Admin login(Admin admin) {
        Admin adminByEmail = adminRepository.findAdminByEmail(admin.getEmail());

        if (adminByEmail == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильна електронна адреса або пароль");
        }

        if (!bCryptPasswordEncoder.matches(admin.getPassword(), adminByEmail.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильна електронна адреса або пароль");
        }

        return adminByEmail;
    }

    @Override
    @Transactional
    public Admin login(String email, String password) {
        return this.login(new Admin(email, password));
    }

    @Override
    @Transactional
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional
    public int deleteAdmin(int id) {
        Admin admin = adminRepository.findAdminById(id);

        if (admin != null && admin.getEmail().equals("admin@gmail.com")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Неможливо видалити головного адміністратора");
        }

        return adminRepository.deleteAdminById(id);
    }
}
