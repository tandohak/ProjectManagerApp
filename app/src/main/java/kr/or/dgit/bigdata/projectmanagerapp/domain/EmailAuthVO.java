package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class EmailAuthVO {
	private String email;
	private String authKey;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public EmailAuthVO() {
	}

	public EmailAuthVO(String email, String authKey) {
		this.email = email;
		this.authKey = authKey;
	}

	@Override
	public String toString() {
		return "EmailAuthVO [email=" + email + ", authKey=" + authKey + "]";
	}
}
