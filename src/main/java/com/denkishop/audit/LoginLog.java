package com.denkishop.audit;


import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.denkishop.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "audit_log")
@DynamicInsert 
@DynamicUpdate 
public class LoginLog {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String emailAddress;
	    private String ipAddress;
	    private String status;
	    private String securityChallenge;
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
		@Temporal(TemporalType.TIMESTAMP)
		private Date created_at;
		@Temporal(TemporalType.TIMESTAMP)
		private Date updated_at;
		public LoginLog() {
			super();
			// TODO Auto-generated constructor stub
		}
		public LoginLog(String emailAddress, String ipAddress, String status, String securityChallenge, User user,
				Date created_at, Date updated_at) {
			super();
			this.emailAddress = emailAddress;
			this.ipAddress = ipAddress;
			this.status = status;
			this.securityChallenge = securityChallenge;
			this.user = user;
			this.created_at = created_at;
			this.updated_at = updated_at;
		}

		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public String getIpAddress() {
			return ipAddress;
		}
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getSecurityChallenge() {
			return securityChallenge;
		}
		public void setSecurityChallenge(String securityChallenge) {
			this.securityChallenge = securityChallenge;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}


		public Date getCreated_at() {
			return created_at;
		}


		public void setCreated_at(Date created_at) {
			this.created_at = created_at;
		}


		public Date getUpdated_at() {
			return updated_at;
		}


		public void setUpdated_at(Date updated_at) {
			this.updated_at = updated_at;
		}
	    
	    

}
