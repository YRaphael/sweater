package com.sweater.sweater.controller;

import com.sweater.sweater.domain.dto.CaptchaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtils {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";


    static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(fe -> fe.getField() + "Error", FieldError::getDefaultMessage));
    }

    protected static CaptchaResponseDTO getCaptchaResponseDTO(@RequestParam("g-recaptcha-response") String captchaResponse, String secret, RestTemplate restTemplate) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        return restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);
    }
}
