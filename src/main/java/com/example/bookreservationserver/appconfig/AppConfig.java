package com.example.bookreservationserver.appconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages="com.example.bookreservationserver")
public class AppConfig {

}
