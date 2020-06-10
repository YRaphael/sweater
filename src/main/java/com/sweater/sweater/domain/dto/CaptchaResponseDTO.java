package com.sweater.sweater.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CaptchaResponseDTO {
    private boolean success;
    @JsonAlias(value = "error-codes")
    private Set<String> error_codes;
}
