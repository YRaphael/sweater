package com.sweater.sweater.controller;

import com.sweater.sweater.domain.Role;
import com.sweater.sweater.domain.User;
import com.sweater.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Map<String, Object> model) {
        model.put("users", userService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Map<String, Object> model) {
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, Object> form,
            @RequestParam("userId") User user,
            Map<String, Object> model) {

        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String editProfile(Model model, @AuthenticationPrincipal User user, @RequestParam String password, @RequestParam String password2, @RequestParam String email) {
        boolean isEdited = userService.updateProfile(user, password, password2, email, model);
        model.addAttribute("username",user.getUsername());
        if (!isEdited){
            return "profile";
        }
        return "redirect:/user/profile";
    }
}
