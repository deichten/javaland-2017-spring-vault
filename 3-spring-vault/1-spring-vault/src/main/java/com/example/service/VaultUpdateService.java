package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;

import java.util.HashMap;
import java.util.Map;

@Service
public class VaultUpdateService {

    private VaultOperations vops;

    @Autowired
    public VaultUpdateService(VaultOperations vops) {
        this.vops = vops;
    }

    public void updateApiKey(String key) {
        Map<String, String> data = new HashMap<>();
        data.put("key", key);
        data.put("lease", "10s");
        vops.write("secret/openweather/api", data);
    }

}
