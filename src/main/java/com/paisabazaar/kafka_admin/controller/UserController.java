package com.paisabazaar.kafka_admin.controller;

import com.paisabazaar.kafka_admin.exception.ResourceNotFoundException;
import com.paisabazaar.kafka_admin.model.User;
import com.paisabazaar.kafka_admin.payload.*;
import com.paisabazaar.kafka_admin.repository.UserRepository;
import com.paisabazaar.kafka_admin.security.CurrentUser;
import com.paisabazaar.kafka_admin.security.UserPrincipal;

import com.paisabazaar.kafka_admin.util.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AppUtils appUtils;

    private static final Logger logger = LogManager.getLogger("UserController");

    public UserController(@Qualifier("userRepository") UserRepository userRepository, PasswordEncoder passwordEncoder, AppUtils appUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUtils = appUtils;
    }

    @PostMapping("/user/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@CurrentUser UserPrincipal currentUser, @RequestBody UpdateUserRequest updateUserRequest) {
        Optional<User> existing = userRepository.findById(currentUser.getId());
        if (updateUserRequest.getPassword() != null) {
            updateUserRequest.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }
        //noinspection OptionalGetWithoutIsPresent
        appUtils.copyNonNullProperties(updateUserRequest, existing.get());
        userRepository.save(existing.get());
        return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully"));
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
    }
}
