package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.RegisterRequest;
import com.backend.portfolio_ac.entity.User;
import com.backend.portfolio_ac.entity.VerificationCode;
import com.backend.portfolio_ac.exception.VerificationEmailException;
import com.backend.portfolio_ac.repository.UserRepository;
import com.backend.portfolio_ac.repository.VerificationCodeRepository;
import com.backend.portfolio_ac.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;
    private static final String CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    @Value("${azure.mail.sender}")
    private String senderEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<String> verifyEmail(String code){
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code);

        if (verificationCode == null){
            throw new VerificationEmailException("Token invalido", VerificationEmailException.Type.CODE_INVALID);
        }
        if (verificationCode.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new VerificationEmailException("Token expired", VerificationEmailException.Type.CODE_EXPIRED);
        }

        User user = verificationCode.getUser();
        if (user.isEmailVerified()){
            throw new VerificationEmailException("El correo ya fue verificado", VerificationEmailException.Type.EMAIL_VERIFIED);
        }
        user.setEmailVerified(true);
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);

        return ResponseEntity.ok("¡Correo verificado correctamnete!");
    }

    @Override
    public String generateVerificationCode(String email) {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int idx = random.nextInt(CODE_CHARS.length());
            code.append(CODE_CHARS.charAt(idx));
        }
        return code.toString();
    }

    @Override
    public void sendVerificationEmail(RegisterRequest request, String accessToken) {

        //Recuperar code de bd
        VerificationCode verificationToken = verificationCodeRepository.findByUser_Email(request.getEmail());

        String url = "https://graph.microsoft.com/v1.0/users/" + senderEmail + "/sendMail";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> message = new HashMap<>();
        message.put("subject", "Verifica tu cuenta");
        message.put("body", Map.of(
                "contentType", "HTML",
                "content", "<h1>Bienvenido</h1><p>Tu código de verificación es: <b>" + verificationToken.getCode() + "</b></p>"
        ));
        message.put("toRecipients", List.of(
                Map.of("emailAddress", Map.of("address", request.getEmail()))
        ));

        Map<String, Object> payload = Map.of(
                "message", message,
                "saveToSentItems", "false"
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error enviando correo: " + response.getBody());
        }
    }
}
