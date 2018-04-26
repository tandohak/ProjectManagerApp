package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class UserVO {
	private int uno;
	private String email;
	private String firstName;
	private String lastName;
	private String phone;
	private String addr;
	private String password;
	private Date birthday;
	private int grade;
	private Date joinDate;
	private String photoPath;

	
	public UserVO() {}
	
	public UserVO(int uno, String email, String firstName, String lastName, String phone, String addr, String password,
			Date birthday, int grade, Date joinDate, String photoPath) {
		super();
		this.uno = uno;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.addr = addr;
		this.password = password;
		this.birthday = birthday;
		this.grade = grade;
		this.joinDate = joinDate;
		this.photoPath = photoPath;
	}


	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "UserVO [uno=" + uno + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phone=" + phone + ", addr=" + addr + ", password=" + password + ", birthday=" + birthday
				+ ", grade=" + grade + ", joinDate=" + joinDate + ", photoPath=" + photoPath + "]";
	}

}
