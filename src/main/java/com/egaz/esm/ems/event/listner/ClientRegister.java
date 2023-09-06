package com.egaz.esm.ems.event.listner;


import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.services.ClientsService;
import com.egaz.esm.ems.event.ClientCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRegister implements ApplicationListener<ClientCompleteEvent> {
    private final ClientsService clientsService;
    private final JavaMailSender mailSender;

    private Client theClient;
    @Override
    public void onApplicationEvent(ClientCompleteEvent event) {
        // 1. Get the newly registered user
        theClient = event.getClient();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        clientsService.saveUserVerificationToken(theClient, verificationToken);
        //4 Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //5. Send the email.
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }
    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "ESM Email Verification";
        String senderName = "Users Registration Portal Service";
        String mailContent = "<p> Hi, "+ theClient.getInstituteName()+ ", </p>"+
                "<p>Thank you for registering with us,"+" " +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> ESM Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("abrahmanabdallah35@gmail.com", senderName);
        messageHelper.setTo(theClient.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }


}
