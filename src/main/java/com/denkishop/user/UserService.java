package com.denkishop.user;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.denkishop.security.JwtRequest;
import com.denkishop.security.JwtResponse;
import com.denkishop.security.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
 // ------------------- JWT -------------------
 	@Autowired
 	private AuthenticationManager authenticationManager;
 	@Autowired
 	private JwtUtils jwtGenerator;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            // Update other user properties as needed
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

	public ResponseEntity<?> authLogin(HttpServletRequest request, JwtRequest loginrequest) {
		// check user before dealing with JWT
		Optional<User> foundusr = userRepository.findByUsername(loginrequest.getUsername());
		if (foundusr.isPresent() ) {

			return new ResponseEntity<>("Did you activate your account through email ? if yes contact us for support  ",
					HttpStatus.UNAUTHORIZED);
		}
		if (foundusr.isPresent()) {
			// SecurityContextHolder , set security context for access
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginrequest.getUsername(), loginrequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Generate token
			try {
				String token = jwtGenerator.generateToken(authentication);
				if (token != null) {
					return new ResponseEntity<>(new JwtResponse(token, foundusr), HttpStatus.FOUND);

				} else {
					return new ResponseEntity<>("Failed to generate Token for the user " + loginrequest.getUsername(),
							HttpStatus.FOUND);
				}
			} catch (Exception e) {
				//System.err.println("error :" + e);
				return new ResponseEntity<>("Bad credentils or Token expired :" + e, HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<>("Username " + loginrequest.getUsername() + " not found !",
					HttpStatus.NOT_FOUND);
		}

	}
}
