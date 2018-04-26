package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class WorkspaceVO {
	private String wcode;
	private String name;
	private String maker;
	private int uno;
	private Date regDate;

	public WorkspaceVO() {}

	public WorkspaceVO(String wcode, String name, String maker, int uno) {
		super();
		this.wcode = wcode;
		this.name = name;
		this.maker = maker;
		this.uno = uno;
	}

	public WorkspaceVO(String wcode, String name, String maker, int uno, Date regDate) {
		super();
		this.wcode = wcode;
		this.name = name;
		this.maker = maker;
		this.uno = uno;
		this.regDate = regDate;
	}

	public String getWcode() {
		return wcode;
	}

	public void setWcode(String wcode) {
		this.wcode = wcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "WorkspaceVO [wcode=" + wcode + ", name=" + name + ", maker=" + maker + ", uno=" + uno + ", regDate="
				+ regDate + "]";
	}

}
