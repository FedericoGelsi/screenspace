package com.uade.ad.controller;

import com.uade.ad.controller.dto.JwtRefreshRequestDto;
import com.uade.ad.controller.dto.JwtResponseDto;
import com.uade.ad.controller.dto.ResetPasswordDto;
import com.uade.ad.controller.dto.VerifyCodeDto;
import com.uade.ad.model.ResetCode;
import com.uade.ad.model.User;
import com.uade.ad.repository.ResetCodeRepository;
import com.uade.ad.service.RefreshTokenService;
import com.uade.ad.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/auths")
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final ResetCodeRepository resetCodeRepository;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final Random random = new Random();

    public AuthController(RefreshTokenService refreshTokenService, ResetCodeRepository resetCodeRepository, UserService userService, JavaMailSender mailSender) {
        this.refreshTokenService = refreshTokenService;
        this.resetCodeRepository = resetCodeRepository;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @PostMapping("/refresh-token")
    public JwtResponseDto refreshJwt(@RequestBody JwtRefreshRequestDto refreshRequestDto) {
        return refreshTokenService.refreshToken(refreshRequestDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> sendEmailResetPassword(@RequestBody String emailUser) {
        //TODO conexion con proveedor, me sale error de credenciales
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailUser);
        email.setFrom("nuestroMail@gmail.com");

        ResetCode resetCode = new ResetCode();
        resetCode.setResetCode(String.valueOf(random.nextInt(100000-10000) + 10000));

        email.setSubject("MoviesAPP : Reset your password.");
        email.setText("Verification code to reset password: " + resetCode.getResetCode());

        mailSender.send(email);
        resetCodeRepository.save(resetCode);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeDto verifyCodeDto) {
        User user = userService.findByEmail(verifyCodeDto.getEmail());
        if (user == null) return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);

        Optional<ResetCode> code = resetCodeRepository.findByResetCode(verifyCodeDto.getVerificationCode());
        if (code.isEmpty()) return new ResponseEntity<>("Incorrect verification code.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Verification code is correct", HttpStatus.OK);
    }

    @PostMapping("/new-password")
    public ResponseEntity<?> newPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            userService.changePassword(resetPasswordDto.getEmail(),resetPasswordDto.getNewPassword());
            return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}