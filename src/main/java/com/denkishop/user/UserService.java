package com.denkishop.user;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.denkishop.mySecurity.RoleNames;
import com.denkishop.utils.ResourceResponse;

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
		Date currentDate = new Date();
		user.setUpdated_at(currentDate);
		user.setCreated_at(currentDate);
		// Encrypt the password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Validate and set the role
		if (user.getRole() != null && isValidRole(user.getRole())) {
			user.setRole(user.getRole());
		} else {
			throw new IllegalArgumentException("Invalid role: " + user.getRole());
		}
		return userRepository.save(user);
	}

	public ResourceResponse<User> updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).orElse(null);
		if (existingUser != null) {
			if (user.getUsername() != null) {
				existingUser.setUsername(user.getUsername());
			}
			if (user.getPassword() != null) {
				existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			if (user.getRole() != null && isValidRole(user.getRole())) {
				existingUser.setRole(user.getRole());
			} else {
				throw new IllegalArgumentException("Invalid role: " + user.getRole());
			}
			if (user.getEmail() != null) {
				existingUser.setEmail(user.getEmail());
			}
			existingUser.setActive(user.isActive());

			User savedUser = userRepository.save(existingUser);
			String message = "User with ID " + id + " updated successfully!";
			return new ResourceResponse<>(savedUser, message);
		}
		String errorMessage = "User with ID " + id + " not found!";
		return new ResourceResponse<>(null, errorMessage);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	private boolean isValidRole(String role) {
		for (RoleNames validRole : RoleNames.values()) {
			if (validRole.name().equals(role)) {
				return true;
			}
		}
		return false;
	}

}
