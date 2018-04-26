package kr.or.dgit.bigdata.projectmanagerapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class UserVO implements Parcelable{
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

	protected UserVO(Parcel in) {
		final ClassLoader cl = getClass().getClassLoader();

		uno = in.readInt();
		email = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		phone = in.readString();
		addr = in.readString();
		password = in.readString();
		birthday = (Date) in.readValue(cl);
		grade = in.readInt();
		joinDate = (Date) in.readValue(cl);
		photoPath = in.readString();
	}

	public static final Creator<UserVO> CREATOR = new Creator<UserVO>() {
		@Override
		public UserVO createFromParcel(Parcel in) {
			return new UserVO(in);
		}

		@Override
		public UserVO[] newArray(int size) {
			return new UserVO[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.uno);
		dest.writeString(this.email);
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.phone);
		dest.writeString(this.addr);
		dest.writeString(this.password);
		dest.writeValue(this.birthday);
		dest.writeInt(this.grade);
		dest.writeValue(this.joinDate);
		dest.writeString(this.photoPath);
	}
}
