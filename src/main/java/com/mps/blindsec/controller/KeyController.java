package com.mps.blindsec.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.constraints.NotNull;

import com.mps.blindsec.dto.EncryptDTO;
import com.mps.blindsec.model.User;
import com.mps.blindsec.service.KeyService;
import com.mps.blindsec.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/key")
public class KeyController {
    static byte[] encrypMessage;

    @Autowired
    private UserService userService;

    @Autowired
    private KeyService keyService;

    @GetMapping("/")
    public ResponseEntity<byte[]> encrypt(@RequestBody @NotNull EncryptDTO textToEncrypt, @RequestParam("name") @NotNull String userName) throws UnsupportedEncodingException {
        User user = userService.findUserByName(userName);
        if (user == null) return null;
        encrypMessage = keyService.encrypt(textToEncrypt, user);
        return ResponseEntity.ok(encrypMessage);
    }

}