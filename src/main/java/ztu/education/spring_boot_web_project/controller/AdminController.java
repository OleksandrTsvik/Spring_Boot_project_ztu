package ztu.education.spring_boot_web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.AdminSaveDTO;
import ztu.education.spring_boot_web_project.service.AdminService;
import ztu.education.spring_boot_web_project.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("admin", new AdminSaveDTO());

        return "formAdmin";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("admin") AdminSaveDTO adminSaveDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("admin", adminSaveDTO);

            return "formAdmin";
        }

        try {
            adminService.register(adminSaveDTO);

            model.addAttribute("admin", new AdminSaveDTO());
            model.addAttribute("successMessage", "Адміністратора успішно збережено");
        } catch (ResponseStatusException e) {
            model.addAttribute("admin", adminSaveDTO);
            model.addAttribute("dangerMessage", e.getReason());
        }

        return "formAdmin";
    }

    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public String admins(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());

        return "listAdmins";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editAdmin(@PathVariable("id") int id, Model model) {
        AdminSaveDTO admin = adminService.getAdminSaveDTO(id);

        if (admin == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("admin", admin);

        return "formAdmin";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteAdmin(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("deleteMessage", "Кількість видалених записів = " + adminService.deleteAdmin(id));
        } catch (ResponseStatusException e) {
            model.addAttribute("deleteMessage", e.getReason());
        }

        model.addAttribute("admins", adminService.getAllAdmins());

        return "listAdmins";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "listUsers";
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("infoMessage", "Кількість видалених записів = " + userService.deleteUser(id));
        model.addAttribute("users", userService.getAllUsers());

        return "listUsers";
    }

    @RequestMapping(value = "/user/toggle/{id}", method = RequestMethod.GET)
    public String toggleUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("infoMessage", userService.toggleUser(id));
        model.addAttribute("users", userService.getAllUsers());

        return "listUsers";
    }
}
