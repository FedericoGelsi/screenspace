package com.uade.ad.controller;

import com.uade.ad.controller.dto.*;
import com.uade.ad.model.ResetCode;
import com.uade.ad.model.User;
import com.uade.ad.repository.ResetCodeRepository;
import com.uade.ad.security.JwtUtils;
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

@RestController
@RequestMapping("/api/auths")
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final ResetCodeRepository resetCodeRepository;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final Random random = new Random();
    private final JwtUtils jwtUtils;


    public AuthController(RefreshTokenService refreshTokenService, ResetCodeRepository resetCodeRepository, UserService userService, JavaMailSender mailSender, JwtUtils jwtUtils) {
        this.refreshTokenService = refreshTokenService;
        this.resetCodeRepository = resetCodeRepository;
        this.userService = userService;
        this.mailSender = mailSender;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/refresh-token")
    public JwtResponseDto refreshJwt(@RequestBody JwtRefreshRequestDto refreshRequestDto) {
        return refreshTokenService.refreshToken(refreshRequestDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> sendEmailResetPassword(@RequestBody ResetPassEmailDto resetPasswordDto) {
        //TODO conexion con proveedor, me sale error de credenciales
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(resetPasswordDto.getEmail());
        email.setFrom("nuestroMail@gmail.com");

        ResetCode resetCode = new ResetCode();
        resetCode.setResetCode(String.valueOf(random.nextInt(10000-1000) + 1000));

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

    @PostMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestBody TokenVerificationRequestDto requestDto) {
        boolean isValid = jwtUtils.isJwtValid(requestDto.getToken());
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}