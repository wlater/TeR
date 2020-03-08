package com.mps.blindsec.service;

import java.io.UnsupportedEncodingException;

import com.mps.blindsec.model.User;

public interface KeyService {
    byte[] encrypt(String textToEncrypt, User user) throws UnsupportedEncodingException;
}