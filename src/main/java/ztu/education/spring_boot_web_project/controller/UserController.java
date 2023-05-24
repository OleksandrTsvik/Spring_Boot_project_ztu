package ztu.education.spring_boot_web_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import ztu.education.spring_boot_web_project.dto.UserRegisterDTO;
import ztu.education.spring_boot_web_project.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("register", new UserRegisterDTO());

        return "formUserRegister";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("register") UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("register", userRegisterDTO);

        if (bindingResult.hasErrors()) {
            return "formUserRegister";
        }

        try {
            userService.register(userRegisterDTO);
        } catch (ResponseStatusException e) {
            model.addAttribute("registerMessage", e.getReason());
            return "formUserRegister";
        }

        return "redirect:/";
    }
}
