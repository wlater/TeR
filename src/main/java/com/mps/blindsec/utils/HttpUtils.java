package com.mps.blindsec.utils;

import com.mps.blindsec.dto.UserDTO;

import org.springframework.http.ResponseEntity;

public class HttpUtils {

    private final static String ERROR_HEADER = "Xerror-message";

    public static ResponseEntity<UserDTO> notFound(String message) {
        return ResponseEntity.notFound()
                .header(ERROR_HEADER, message)
                .build();
    }

    public static ResponseEntity<UserDTO> badRequest(String message) {
        return ResponseEntity.badRequest()
                .header(ERROR_HEADER, message)
                .build();
    }
}
