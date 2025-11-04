package org.uv.security.service;

import org.uv.security.entity.OTP;
import org.uv.security.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final OtpRepository otpRepository;
    private final MailService mailService;

    @Value("${otp.expiration.minutes:5}")
    private int expirationMinutes;

    @Value("${otp.length:6}")
    private int otpLength;

    private final SecureRandom random = new SecureRandom();

    @Transactional
    public String generateAndSendOtp(String correo, String nombre) {
        // Eliminar OTPs anteriores del mismo correo
        otpRepository.deleteByEmail(correo);

        // Generar código OTP
        String codigo = generateOtpCode();

        // Calcular tiempo de expiración
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expirationMinutes);

        // Guardar OTP en la base de datos
        OTP otp = new OTP();
        otp.setEmail(correo);
        otp.setCode(codigo);
        otp.setExpirationTime(expirationTime);
        otp.setIsUsed(false);

        otpRepository.save(otp);

        // Enviar correo electrónico
        mailService.sendOtpEmail(correo, codigo, nombre);

        log.info("OTP generado y enviado para: {}", correo);
        return codigo;
    }

    @Transactional
    public boolean verifyOtp(String correo, String codigo) {
        Optional<OTP> otpOpt = otpRepository.findByEmailAndCodeAndIsUsedFalseAndExpirationTimeAfter(
                correo,
                codigo,
                LocalDateTime.now()
        );

        if (otpOpt.isPresent()) {
            OTP otp = otpOpt.get();
            otp.setIsUsed(true);
            otpRepository.save(otp);
            log.info("OTP verificado exitosamente para: {}", correo);
            return true;
        }

        log.warn("OTP inválido o expirado para: {}", correo);
        return false;
    }

    private String generateOtpCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    @Transactional
    public void cleanupExpiredOtps() {
        otpRepository.deleteByExpirationTimeBefore(LocalDateTime.now());
        log.info("OTPs expirados eliminados");
    }
}