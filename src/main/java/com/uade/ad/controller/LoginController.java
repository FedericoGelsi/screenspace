package com.uade.ad.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.uade.ad.controller.dto.LoginDto;
import com.uade.ad.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/test")
    public String login() {
        return loginService.test();
    }

    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login Exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content)
    })
    @PostMapping("/")
    @ResponseBody
    public String login(@RequestBody LoginDto dto) {
        return loginService.login(dto);
    }


}