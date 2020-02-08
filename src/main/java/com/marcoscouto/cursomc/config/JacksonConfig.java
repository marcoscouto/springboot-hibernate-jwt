package com.marcoscouto.cursomc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoscouto.cursomc.domain.PaymentCreditCard;
import com.marcoscouto.cursomc.domain.PaymentSlip;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder(){
            public void configure(ObjectMapper objectMapper){
                objectMapper.registerSubtypes(PaymentSlip.class);
                objectMapper.registerSubtypes(PaymentCreditCard.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
}
