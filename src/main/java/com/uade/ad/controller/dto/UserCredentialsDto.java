package com.uade.ad.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCredentialsDto {

    @NotBlank
    @Schema(example = "user@mail.com", required = true)
    private String email;

    @NotBlank
    @Schema(example = "my-password", required = true)
    private String password;

    private String tokenGoogle;
}
