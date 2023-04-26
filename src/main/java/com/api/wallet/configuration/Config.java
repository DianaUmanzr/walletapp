package com.api.wallet.configuration;

import exception.ExceptionAdvice;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ExceptionAdvice exceptionAdvice() {
        return new ExceptionAdvice();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
