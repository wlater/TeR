package com.mps.blindsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.mps.blindsec.dto.EncryptDTO;
import com.mps.blindsec.model.User;
import com.mps.blindsec.utils.KeyUtils;

@Component
public class KeyServiceImpl implements KeyService, Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private KeyUtils keyUtils;

    @Override
    public byte[] encrypt(EncryptDTO textToEncrypt, User user) throws UnsupportedEncodingException {
        PublicKey pkey;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(textToEncrypt);
            oos.flush();
            byte [] data = bos.toByteArray();
            pkey = keyUtils.readPublicKey(user);
            return KeyUtils.encrypt(data, pkey);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
