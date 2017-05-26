/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service.implementation;

import org.apache.velocity.app.VelocityEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import springbackend.service.EmailService;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of {@link springbackend.service.EmailService} interface.
 */
@Service
public class EmailServiceImpl implements EmailService{
    private static final String FROM = "from";

    private static final String TO = "to";

    private static final String SUBJECT = "from";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    public boolean sendEmail(final String templateName, final Map<String, Object> model) {
        boolean result = false;
        try {
            MimeMessagePreparator preparator = (mimeMessage) -> {

                String from = (String) model.get(FROM);
                String to = (String) model.get(TO);
                String subject = (String) model.get(SUBJECT);

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setSentDate(new Date());

                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        this.velocityEngine, templateName, "UTF-8", model);

                message.setText(text, true);
            };

            this.mailSender.send(preparator);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String generateString(int length) {
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOASDFGHJKLZXCVBNM";
        Random random = new Random();
        char[] text = new char[length];

        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}
