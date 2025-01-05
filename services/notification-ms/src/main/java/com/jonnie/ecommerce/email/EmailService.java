package com.jonnie.ecommerce.email;

import com.jonnie.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference) {
        Map<String, Object> variables = Map.of(
                "customerName", customerName,
                "amount", amount,
                "orderReference", orderReference
        );

        prepareAndSendEmail(destinationEmail, EmailTemplates.PAYMENT_CONFIRMATION, variables);
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products) {
        Map<String, Object> variables = Map.of(
                "customerName", customerName,
                "totalamount", amount,
                "orderReference", orderReference,
                "products", products
        );

        prepareAndSendEmail(destinationEmail, EmailTemplates.ORDER_CONFIRMATION, variables);
    }

    private void prepareAndSendEmail(
            String destinationEmail,
            EmailTemplates template,
            Map<String, Object> variables) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                    MULTIPART_MODE_RELATED,
                    StandardCharsets.UTF_8.name());

            messageHelper.setFrom("jonnie@proton.me");
            messageHelper.setSubject(template.getSubject());
            messageHelper.setTo(destinationEmail);

            Context context = new Context();
            context.setVariables(variables);

            String html = templateEngine.process(template.getTemplateName(), context);
            messageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);
            log.info("Email sent to: <{}>", destinationEmail);
        } catch (MessagingException e) {
            log.warn("Failed to send email to: <{}>", destinationEmail, e);
        }
    }
}
