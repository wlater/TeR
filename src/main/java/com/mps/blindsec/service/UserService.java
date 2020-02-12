package com.mps.blindsec.service;

import java.io.IOException;

import com.mps.blindsec.exceptions.InvalidPublicKeyException;
import com.mps.blindsec.model.User;

public interface UserService {
    String PUBLIC_KEY_NAME = "pkey.der";
    String ACTUAL_STORAGE_PATH = "./keys";

    User register(User user);
    User findUserByEmail(String email);
    User findUserByName(String email);
    void updatePublicKey(User user, byte[] content) throws InvalidPublicKeyException, IOException;
    boolean removeUser(Long UserId) throws IOException, InvalidPublicKeyException;
}