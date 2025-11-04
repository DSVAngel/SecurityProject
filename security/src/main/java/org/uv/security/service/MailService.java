package org.uv.security.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String codigo, String nombre) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Tu código de verificación");

            String htmlContent = buildEmailContent(codigo, nombre);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Correo OTP enviado exitosamente a: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Error al enviar correo a: {}", toEmail, e);
            throw new RuntimeException("Error al enviar el correo electrónico");
        }
    }

    private String buildEmailContent(String codigo, String nombre) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
                    .container { max-width: 600px; margin: 50px auto; background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
                    .header { text-align: center; color: #333; }
                    .otp-code { font-size: 32px; font-weight: bold; color: #4CAF50; text-align: center; padding: 20px; background-color: #f0f0f0; border-radius: 5px; margin: 20px 0; letter-spacing: 5px; }
                    .footer { text-align: center; color: #777; font-size: 12px; margin-top: 30px; }
                    .warning { color: #ff5722; font-size: 14px; text-align: center; margin-top: 10px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>¡Hola, %s!</h1>
                        <p>Has solicitado acceso a tu cuenta. Usa el siguiente código de verificación:</p>
                    </div>
                    <div class="otp-code">%s</div>
                    <p style="text-align: center; color: #555;">Este código expirará en <strong>5 minutos</strong>.</p>
                    <div class="warning">
                        <p>⚠️ Si no solicitaste este código, ignora este correo.</p>
                    </div>
                    <div class="footer">
                        <p>Este es un correo automático, por favor no respondas.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(nombre, codigo);
    }
}
