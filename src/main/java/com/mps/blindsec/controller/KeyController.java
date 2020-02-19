package com.mps.blindsec.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.NotNull;

import com.mps.blindsec.model.User;
import com.mps.blindsec.service.KeyService;
import com.mps.blindsec.service.UserService;
import com.mps.blindsec.utils.KeyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<byte[]> encrypt(@RequestParam("text") @NotNull String textToEncrypt, @RequestParam("name") @NotNull String userName) throws UnsupportedEncodingException {
        User user = userService.findUserByName(userName);
        if (user == null) return null;
        encrypMessage = keyService.encrypt(textToEncrypt, user);
        return ResponseEntity.ok(encrypMessage);
    }
    /**
     * ! NEED TO BE DONE ON CLIENT SIDE ONLY, CUZ WE SHOULDNT HOLD A PrivateKey OBJECT ON THE SEVER SIDE
     * TODO: CHANGE IT TO MultipartFile
     * @param textToDecrypt
     * @param privateKey
     * @return String
     */
    @GetMapping
	public ResponseEntity<String> decrypt(@RequestParam("text") @NotNull byte[] textToDecrypt, @RequestParam("file") @NotNull PrivateKey privateKey){
        try {
            return ResponseEntity.ok(KeyUtils.decrypt(privateKey, textToDecrypt));
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
            System.err.println(e);
        }
        return null;
	}    
}