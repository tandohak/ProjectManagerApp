package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class LoginDTO {
	private int uno;
	private String email;
	private String password;
	private String username;
	private String photoPath;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "LoginDTO [uno=" + uno + ", email=" + email + ", password=" + password + ", username=" + username
				+ ", photoPath=" + photoPath + "]";
	}


}
