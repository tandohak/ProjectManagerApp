package kr.or.dgit.bigdata.projectmanagerapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class MemberVO implements Parcelable {
	private int mno;
	private int uno;
	private String wcode;
	private int memGrade;
	
	//join을 위한 필드
	private String name; // 워크스페이스 이름
	private String firstName; // 멤버 성
	private String lastName; // 멤버 이름
	private String photoPath;
	private String email;
	private int massno; //멤버 배정번호
	private int memAssGrade; //멤버 배정 등급

	public MemberVO() {
	}

	public MemberVO(int mno, int uno, String wcode, int memGrade, String name, String firstName, String lastName, String photoPath, String email, int massno, int memAssGrade) {
		this.mno = mno;
		this.uno = uno;
		this.wcode = wcode;
		this.memGrade = memGrade;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoPath = photoPath;
		this.email = email;
		this.massno = massno;
		this.memAssGrade = memAssGrade;
	}

	protected MemberVO(Parcel in) {
		mno = in.readInt();
		uno = in.readInt();
		wcode = in.readString();
		memGrade = in.readInt();
		name = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		photoPath = in.readString();
		email = in.readString();
		massno = in.readInt();
		memAssGrade = in.readInt();
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mno);
		dest.writeInt(this.uno);
		dest.writeString(this.wcode);
		dest.writeInt(this.memGrade);
		dest.writeString(this.name);
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.photoPath);
		dest.writeString(this.email);
		dest.writeInt(this.massno);
		dest.writeInt(this.memAssGrade);
	}
	public static final Creator<MemberVO> CREATOR = new Creator<MemberVO>() {
		@Override
		public MemberVO createFromParcel(Parcel in) {
			return new MemberVO(in);
		}

		@Override
		public MemberVO[] newArray(int size) {
			return new MemberVO[size];
		}
	};

	public int getMassno() {
		return massno;
	}
	public void setMassno(int massno) {
		this.massno = massno;
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
	public String getPhotoPath() {
		return photoPath; 
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public String getWcode() {
		return wcode;
	}
	public void setWcode(String wcode) {
		this.wcode = wcode;
	}
	public int getMemGrade() {
		return memGrade;
	}
	public void setMemGrade(int memGrade) {
		this.memGrade = memGrade;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMemAssGrade() {
		return memAssGrade;
	}
	public void setMemAssGrade(int memAssGrade) {
		this.memAssGrade = memAssGrade;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("MemberVO{");
		sb.append("mno=").append(mno);
		sb.append(", uno=").append(uno);
		sb.append(", wcode='").append(wcode).append('\'');
		sb.append(", memGrade=").append(memGrade);
		sb.append(", name='").append(name).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append(", photoPath='").append(photoPath).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", massno=").append(massno);
		sb.append(", memAssGrade=").append(memAssGrade);
		sb.append('}');
		return sb.toString();
	}


}
