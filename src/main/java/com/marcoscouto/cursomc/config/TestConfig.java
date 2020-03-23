package com.marcoscouto.cursomc.config;

import com.marcoscouto.cursomc.services.DBService;
import com.marcoscouto.cursomc.services.EmailService;
import com.marcoscouto.cursomc.services.S3Service;
import com.marcoscouto.cursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"test", "dev"})
public class TestConfig implements CommandLineRunner {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DBService databaseService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Override
    public void run(String... args) throws Exception {
        if (strategy.equals("create") || strategy.equals("update"))
            databaseService.populateDatabase();

        s3Service.uploadFile("/Users/marcoscouto/Pictures/foto.jpeg");
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
