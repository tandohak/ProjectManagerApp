package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class ProjectVO {
	private int pno;
	private String wcode;
	private String title;
	private int maker;
	private String explanation;
	private boolean visibility;
	private Date regDate;
	private Date startDate;
	private Date endDate;
	private Date finishDate;
	private int status;
	private int authority;
	private boolean locker;

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public int getMaker() {
		return maker;
	}

	public void setMaker(int maker) {
		this.maker = maker;
	}

	public String getWcode() {
		return wcode;
	}

	public void setWcode(String wcode) {
		this.wcode = wcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public boolean isLocker() {
		return locker;
	}

	public void setLocker(boolean locker) {
		this.locker = locker;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "ProjectVO [pno=" + pno + ", wcode=" + wcode + ", title=" + title + ", explanation=" + explanation
				+ ", visibility=" + visibility + ", regDate=" + regDate + ", startDate=" + startDate + ", endDate="
				+ endDate + ", finishDate=" + finishDate + ", status=" + status + ", authority=" + authority
				+ ", locker=" + locker + "]";
	}


}
