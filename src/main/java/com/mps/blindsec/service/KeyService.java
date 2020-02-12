package com.mps.blindsec.service;

import java.io.UnsupportedEncodingException;

import com.mps.blindsec.model.User;

public interface KeyService {
    String PUBLIC_KEY_NAME = "pkey.der";
    String ACTUAL_STORAGE_PATH = "./keys";

    byte[] encrypt(String textToEncrypt, User user) throws UnsupportedEncodingException;
}