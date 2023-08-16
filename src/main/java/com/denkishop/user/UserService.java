package com.denkishop.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
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


}
