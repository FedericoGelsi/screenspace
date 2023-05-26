package com.uade.ad.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    @NotBlank
    @Schema(example = "user@mail.com", required = true)
    private String email;

    @NotBlank
    @Schema(example = "my-password", required = true)
    private String password;

    @NotBlank
    @Schema(example = "admin or client", required = true)
    private String role;
}
