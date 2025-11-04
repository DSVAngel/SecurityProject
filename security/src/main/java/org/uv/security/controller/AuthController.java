package org.uv.security.controller;


import org.uv.security.dto.*;
import org.uv.security.entity.User;
import org.uv.security.service.OtpService;
import org.uv.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserResponse userResponse = userService.registerUser(request);

            // Generar y enviar OTP
            otpService.generateAndSendOtp(request.getEmail(), request.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            true,
                            "Usuario registrado exitosamente. Se ha enviado un código OTP a tu correo.",
                            userResponse
                    ));
        } catch (RuntimeException e) {
            log.error("Error en registro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

            if (!userService.verifyPassword(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(false, "Credenciales inválidas"));
            }

            // Generar y enviar OTP
            otpService.generateAndSendOtp(user.getEmail(), user.getName());

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Se ha enviado un código OTP a tu correo.",
                    userService.getUserResponse(user)
            ));
        } catch (RuntimeException e) {
            log.error("Error en login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Credenciales inválidas"));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        try {
            boolean isValid = otpService.verifyOtp(request.getEmail(), request.getCode());

            if (!isValid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Código OTP inválido o expirado"));
            }

            // Marcar usuario como verificado
            userService.markUserAsVerified(request.getEmail());

            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "¡Acceso exitoso! Bienvenido.",
                    userService.getUserResponse(user)
            ));
        } catch (Exception e) {
            log.error("Error en verificación OTP: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error al verificar el código"));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse> resendOtp(@RequestBody ResendOtpRequest request) {
        try {
            User user = userService.findByEmail(request.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            otpService.generateAndSendOtp(user.getEmail(), user.getName());

            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Se ha reenviado el código OTP a tu correo."
            ));
        } catch (Exception e) {
            log.error("Error al reenviar OTP: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}

class ResendOtpRequest {
    private String correo;

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}