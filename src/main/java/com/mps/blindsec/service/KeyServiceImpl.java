package com.mps.blindsec.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.mps.blindsec.model.User;
import com.mps.blindsec.utils.KeyUtils;

@Component
public class KeyServiceImpl implements KeyService {

    @Override
    public byte[] encrypt(String textToEncrypt, User user) throws UnsupportedEncodingException {
        PublicKey pkey;
        byte[] plainText = textToEncrypt.getBytes("UTF8");
            try {
                pkey = KeyUtils.readPublicKey(user);
                return KeyUtils.encrypt(plainText, pkey);
            } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
                e.printStackTrace();
            }
        return null;
    }

}
