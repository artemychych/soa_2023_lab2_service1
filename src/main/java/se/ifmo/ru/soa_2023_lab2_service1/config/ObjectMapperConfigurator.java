package se.ifmo.ru.soa_2023_lab2_service1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperConfigurator {
    @Produces
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }
}
