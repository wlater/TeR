package com.mps.blindsec.utils;

import com.mps.blindsec.model.User;
import com.mps.blindsec.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtils {
    public static final String SALT = "jajaba";

    public static byte[] readFileBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static PublicKey readPublicKey(User user) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Path key = keyFromUser(user);
        return readPublicKey(key.toAbsolutePath().toString());
    }

    public static PublicKey readPublicKey(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    public static PrivateKey readPrivateKey(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
    
    public static boolean isKeyValid(User user) {
        return isKeyValid(keyFromUser(user));
    }

    public static boolean isKeyValid(Path keyPath) {
        try {
            readPublicKey(keyPath.toAbsolutePath().toString());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return false;
        }
        return true;
    }

    public static Path keyFromUser(User user) {
        return Paths.get(UserService.ACTUAL_STORAGE_PATH , user.getId().toString(), UserService.PUBLIC_KEY_NAME );
    }

    public static String hashingPassword(String password) {
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);
		return hashedPassword;
	}

    public static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
		}

		return hash.toString();
	}
}
