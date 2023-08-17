package com.denkishop.audit;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login-log")
public class LoginLogController {

	@Autowired
	private LoginLogService loginLogService;

	@GetMapping("/{id}")
	public ResponseEntity<LoginLog> getLoginLogById(@PathVariable Long id) {
		Optional<LoginLog> loginLog = loginLogService.getLoginLogById(id);
		return loginLog.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/all")
	public ResponseEntity<List<LoginLog>> getAllLoginLogs() {
		List<LoginLog> loginLogs = loginLogService.getAllLoginLogs();
		return ResponseEntity.ok(loginLogs);
	}

	@PostMapping("/new")
	public ResponseEntity<LoginLog> logLogin(@RequestBody LoginLog loginLog) {
		LoginLog savedLoginLog = loginLogService.saveLoginLog(loginLog);
		return ResponseEntity.ok(savedLoginLog);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<LoginLog> updateLoginLog(@PathVariable Long id, @RequestBody LoginLog loginLog) {
		LoginLog updatedLoginLog = loginLogService.updateLoginLog(id, loginLog);
		if (updatedLoginLog != null) {
			return ResponseEntity.ok(updatedLoginLog);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
