package com.hanapsim.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table (name = "userDetail")
public class UserDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
//	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
//    @JoinColumn(name="userId", nullable = false, insertable=false, updatable=false)
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String middleName;
	private Date birthDate;
	private String school;
	private String company;
	private String occupation;
	private String gender;
	@Lob
	@Basic(fetch=FetchType.LAZY) // this gets ignored anyway, but it is recommended for blobs
	private  byte[] picture;
	
//    private Sim sim = new Sim();

//	public Sim getSim() {
//		return sim;
//	}
//
//	public void setSim(Sim sim) {
//		this.sim = sim;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDetail) {
			UserDetail userDetail = (UserDetail) obj;
			return userDetail.getId() == id;
		}

		return false;
	}

//	public void setPicture(InputStream iStream) {
//		// TODO Auto-generated method stub
////		this.picture. = picture;
//	}

//	public void setPicture(InputStream iStream, byte[] picture) {
//		// TODO Auto-generated method stub
//		this.picture = picture;
//		
//	}
}