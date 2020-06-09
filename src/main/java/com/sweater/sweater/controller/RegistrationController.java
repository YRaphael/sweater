package com.sweater.sweater.controller;

import com.sweater.sweater.domain.User;
import com.sweater.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.put("passwordError", "Password don't match.");
        }

        if (bindingResult.hasErrors()) {
            model.putAll(ControllerUtils.getErrors(bindingResult));

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.put("usernameError", "User already exist");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Map<String, Object> model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.put("message", "User successfully activated");
        } else {
            model.put("message", "Activation code is not found");
        }

        return "login";
    }
}
