package com.endava;

import com.endava.console.Console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.endava")
public class AppConfig {

    @Bean
    public Console getConsole() {
        return new Console();
    }

}
