package com.egaz.esm.ems.clientRegister;


import com.egaz.esm.ems.clientRegister.token.VerificationToken;
import com.egaz.esm.ems.clientRegister.token.VerificationTokenRepository;
import com.egaz.esm.ems.clients.Client;
import com.egaz.esm.ems.clients.dto.ClientRequest;
import com.egaz.esm.ems.clients.services.ClientsService;
import com.egaz.esm.ems.event.ClientCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private ClientsService clientsService;
    private final ApplicationEventPublisher publisher;
    private VerificationTokenRepository tokenRespository;

    @PostMapping
    public String registerUser(@RequestBody ClientRequest registrationRequest, final HttpServletRequest request){
        Client client = clientsService.registerClient(registrationRequest);
        publisher.publishEvent(new ClientCompleteEvent(client, applicationUrl(request)));
        return "Success!  Please, check your email to complete your registration";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRespository.findByToken(token);
        if (theToken.getClient().isEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = clientsService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }


}
