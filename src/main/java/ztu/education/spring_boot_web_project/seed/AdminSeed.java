package ztu.education.spring_boot_web_project.seed;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztu.education.spring_boot_web_project.dto.AdminSaveDTO;
import ztu.education.spring_boot_web_project.service.AdminService;

@Component
public class AdminSeed implements InitializingBean {
    @Autowired
    private AdminService adminService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String adminEmail = "admin@gmail.com";

        if (adminService.findByEmail(adminEmail) == null) {
            AdminSaveDTO adminSaveDTO = new AdminSaveDTO();

            adminSaveDTO.setEmail(adminEmail);
            adminSaveDTO.setPassword("123123");

            adminService.register(adminSaveDTO);
        }
    }
}
