package com.example.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

@Configuration
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Override
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint endpoint = new VaultEndpoint();
        endpoint.setScheme("http");
        return endpoint;
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
    }

}
