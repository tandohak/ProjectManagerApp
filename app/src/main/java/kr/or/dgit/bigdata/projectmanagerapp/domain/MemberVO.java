package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class MemberVO {
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
		return "MemberVO [mno=" + mno + ", uno=" + uno + ", wcode=" + wcode + ", memGrade=" + memGrade + "]";
	}

}
