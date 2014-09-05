package com.hanapsim.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@NamedQuery(name = "User.findUserBySimNumber", query = "select u from User u where u.simNumber = :simNumber")
@Table (name = "user")
public class User implements Serializable  {
	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_SIMNUMBER = "User.findUserBySimNumber";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String simNumber;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private Subscription subscription;
	
	private Boolean isActive;
	@Transient
	private String confirmPassword;
	
	public User() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSimNumber() {
		return simNumber;
	}
	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	//Check user role
	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}

	public boolean isUser() {
		return Role.STANDARD.equals(role);
	}

	public boolean isGuest() {
		return Role.GUEST.equals(role);
	}
	
	
	//Check subscription
	public boolean isFree() {
		return Subscription.FREE.equals(subscription);
	}
	
	public boolean isMonthly() {
		return Subscription.MONTHLY.equals(subscription);
	}
	
	public boolean isYearly() {
		return Subscription.YEARLY.equals(subscription);
	}
	
	
	
	
	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return user.getId() == id;
		}

		return false;
	}
	
	
}
