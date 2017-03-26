package com.example.controller;

import com.example.service.VaultUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/key")
public class VaultController {

    private VaultUpdateService vUpdSvc;

    @Autowired
    public VaultController(VaultUpdateService vUpdSvc) {
        this.vUpdSvc = vUpdSvc;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Void> updateKey(@RequestBody Map<String, String> data) {
        try {
            vUpdSvc.updateApiKey(data.get("key"));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }

    }
}
