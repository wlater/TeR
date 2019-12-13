package com.mps.blindsec.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import com.mps.blindsec.dto.UserDTO;
import com.mps.blindsec.dto.mapper.UserMapper;
import com.mps.blindsec.exceptions.InvalidPublicKeyException;
import com.mps.blindsec.model.User;
import com.mps.blindsec.repository.UserRepository;
import com.mps.blindsec.service.UserService;
import com.mps.blindsec.utils.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail()))
            return HttpUtils.badRequest("This email is already taken");
        User user = userService.register(UserMapper.toUser(userDTO));
        return ResponseEntity.ok(UserMapper.toUserDto(user));

    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam("email") @NotNull String email) {
        User user = userService.findUserByEmail(email);

        if (user == null) return HttpUtils.notFound( "Email " + email + " not found");

        return ResponseEntity.ok(UserMapper.toUserDto(userService.findUserByEmail(email)));
    }

    @GetMapping("/getName")
    public ResponseEntity<UserDTO> getUserByName(@RequestParam("name") @NotNull String name) {

        User user = userService.findUserByName(name);

        if (user == null) return HttpUtils.notFound( "Username " + name + " not found");

        return ResponseEntity.ok(UserMapper.toUserDto(userService.findUserByName(name)));
    }

    @PostMapping("/public-key")
    public ResponseEntity<HttpStatus> updatePublicKey(HttpServletRequest httpRequest, @RequestParam("user_id") @NotNull Long userId,
                                          @RequestParam("file") @NotNull MultipartFile file) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent())
            return ResponseEntity.notFound().build();

        try {
            userService.updatePublicKey(optionalUser.get(), file.getBytes());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidPublicKeyException ipk) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/")
    public ResponseEntity<Boolean> removeKey(@RequestParam("user_id") @NotNull Long userId){
        try {
            return ResponseEntity.ok(userService.removeUser(userId));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidPublicKeyException ipk) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}