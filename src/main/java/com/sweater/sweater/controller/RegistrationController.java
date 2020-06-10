package com.sweater.sweater.controller;

import com.sweater.sweater.domain.User;
import com.sweater.sweater.domain.dto.CaptchaResponseDTO;
import com.sweater.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Map<String, Object> model
    ) {
        CaptchaResponseDTO captchaResponseDTO = ControllerUtils.getCaptchaResponseDTO(captchaResponse, secret, restTemplate);

        boolean captchaNotFilled = !captchaResponseDTO.isSuccess();
        if (captchaNotFilled) {
            model.put("captchaError", "Fill captcha.");
        }

        boolean isConfirmEmpty = passwordConfirm == null || StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.put("password2Error", "Password confirm can't be empty.");
        }

        boolean isPasswordsEqual = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
        if (isPasswordsEqual) {
            model.put("passwordError", "Password don't match.");
        }

        if (captchaNotFilled || isConfirmEmpty || isPasswordsEqual || bindingResult.hasErrors()) {
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
            model.put("messageType", "success");
            model.put("message", "User successfully activated");
        } else {
            model.put("messageType", "danger");
            model.put("message", "Activation code is not found");
        }

        return "login";
    }
}
