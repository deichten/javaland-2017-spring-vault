package com.example.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.annotation.VaultPropertySource;

@Getter
@Setter
@Configuration
@VaultPropertySource(
        value = "secret/openweather/api",
        renewal = VaultPropertySource.Renewal.ROTATE,
        propertyNamePrefix = "openweather."
)
public class ApiProperties {
}