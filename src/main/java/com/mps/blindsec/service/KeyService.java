package com.mps.blindsec.service;

import java.io.UnsupportedEncodingException;

import com.mps.blindsec.dto.EncryptDTO;
import com.mps.blindsec.model.User;

public interface KeyService {
    byte[] encrypt(EncryptDTO textToEncrypt, User user) throws UnsupportedEncodingException;
}