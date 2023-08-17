package com.denkishop.audit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService {
	
	@Autowired
	LoginLogRepository loginLogRepository;
	
	public LoginLog saveLoginLog(LoginLog loginLog) {
        // Set created_at and updated_at as needed
		loginLog.setUpdated_at(new Date());
		loginLog.setCreated_at(new Date());
        return loginLogRepository.save(loginLog);
    }
	

    public Optional<LoginLog> getLoginLogById(Long id) {
        return loginLogRepository.findById(id);
    }

    public List<LoginLog> getAllLoginLogs() {
        return loginLogRepository.findAll();
    }

    public LoginLog updateLoginLog(Long id, LoginLog updatedLoginLog) {
        LoginLog existingLoginLog = loginLogRepository.findById(id).orElse(null);
        if (existingLoginLog != null) {
            // Update fields as needed
            // Update fields if provided
            if (updatedLoginLog.getEmailAddress() != null) {
                existingLoginLog.setEmailAddress(updatedLoginLog.getEmailAddress());
            }
            if (updatedLoginLog.getIpAddress() != null) {
                existingLoginLog.setIpAddress(updatedLoginLog.getIpAddress());
            }
            if (updatedLoginLog.getStatus() != null) {
                existingLoginLog.setStatus(updatedLoginLog.getStatus());
            }
            if (updatedLoginLog.getSecurityChallenge() != null) {
                existingLoginLog.setSecurityChallenge(updatedLoginLog.getSecurityChallenge());
            }
  
            // Set updated_at if needed
            existingLoginLog.setUpdated_at(new Date());

            return loginLogRepository.save(existingLoginLog);
        }
        return null;
    }

}
