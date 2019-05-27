package com.paisabazaar.kafka_admin.controller;

import com.paisabazaar.kafka_admin.exception.ResourceNotFoundException;
import com.paisabazaar.kafka_admin.model.User;
import com.paisabazaar.kafka_admin.payload.UserIdentityAvailability;
import com.paisabazaar.kafka_admin.payload.UserProfile;
import com.paisabazaar.kafka_admin.payload.UserSummary;
import com.paisabazaar.kafka_admin.repository.UserRepository;
import com.paisabazaar.kafka_admin.security.CurrentUser;
import com.paisabazaar.kafka_admin.security.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger("UserController");

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
