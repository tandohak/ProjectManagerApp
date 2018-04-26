package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class MemAssignmentVO {
	private int massno;
	private int pno;
	private int mno;
	private int grade;

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public int getMno() {
		return mno;
	}

	public int getMassno() {
		return massno;
	}

	public void setMassno(int massno) {
		this.massno = massno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public int getGrade() {
		return grade; 
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MemAssignmentVO [pno=" + pno + ", mno=" + mno + ", grade=" + grade + "]";
	}

}
