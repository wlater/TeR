package com.mps.blindsec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.mps.blindsec.exceptions.InvalidPublicKeyException;
import com.mps.blindsec.model.User;
import com.mps.blindsec.repository.UserRepository;
import com.mps.blindsec.utils.FileUtils;
import com.mps.blindsec.utils.KeyUtils;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyUtils keyUtils;
    
    @Override
    public User register(User user) {
        ZonedDateTime date = ZonedDateTime.now(ZoneId.systemDefault());
        user.setPasswordHash(keyUtils.hashingPassword(user.getPasswordHash()));
        user.setCreated(date);
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByName(String name) {
        Optional<User> optionalSession = userRepository.findByName(name);

        if (optionalSession.isPresent()) {
            return optionalSession.get();
        } else {
            return null;
        }
    }

    @Override
    public void updatePublicKey(User user, byte[] content) throws InvalidPublicKeyException, IOException {

        Path storagePath = Paths.get(keyUtils.ACTUAL_STORAGE_PATH);
        FileUtils.createPath(storagePath);

        Path userPath = storagePath.resolve(user.getId().toString());
        FileUtils.createPath(userPath);

        Path keyPath = userPath.resolve(keyUtils.PUBLIC_KEY_NAME);

        Files.write(keyPath, content);

        if (!KeyUtils.isKeyValid(keyPath)) {
            Files.deleteIfExists(keyPath);
            throw new InvalidPublicKeyException(keyPath.toString());
        }

        user.setPublicKeyPath(user.getId() + "/" + keyUtils.PUBLIC_KEY_NAME);
        userRepository.save(user);
    }

    @Override
    public boolean removeUser(Long UserId) throws IOException, InvalidPublicKeyException {
        Optional<User> optionalUser = userRepository.findById(UserId);
        Path userPath = Paths.get(optionalUser.get().getPublicKeyPath());

        if (!optionalUser.isPresent())
            return false;

        KeyUtils.deleteDirectory(userPath.toFile());
        userRepository.delete(optionalUser.get());

        return true;
    }
}
