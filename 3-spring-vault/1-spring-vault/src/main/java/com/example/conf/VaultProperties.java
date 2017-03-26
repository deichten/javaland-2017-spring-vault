package com.example.conf;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("vault")
public class VaultProperties {

    private String address;

    private String token;

}
