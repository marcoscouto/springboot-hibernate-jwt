package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.repositories.ClientRepository;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder bc;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Client client = clientRepository.findByEmail(email);
        if(client == null) throw new ObjectNotFoundException("Email not founded");

        String newPass = newPassword();
        client.setPassword(bc.encode(newPass));

        clientRepository.save(client);

        emailService.sendNewPasswordEmail(client, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        Arrays.asList(vet).forEach(System.out::print);
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0) return (char) (random.nextInt(10) + 48);
        else if (opt == 1) return (char) (random.nextInt(26) + 65);
        else if (opt == 2) return (char) (random.nextInt(26) + 97);
        return '1';
    }


}
