package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class InviteVO {
	private int ino;
	private String wcode;
	private Date inviteDate;
	private String inviter;
	private String invitee;
	
	
	public InviteVO() {}
	
	public InviteVO(int ino, String wcode, Date inviteDate, String inviter, String invitee) {
		this.ino = ino;
		this.wcode = wcode;
		this.inviteDate = inviteDate;
		this.inviter = inviter;
		this.invitee = invitee;
	}

	public int getIno() {
		return ino;
	}
	public void setIno(int ino) {
		this.ino = ino;
	}
	public String getWcode() {
		return wcode;
	}
	public void setWcode(String wcode) {
		this.wcode = wcode;
	}
	public Date getInviteDate() {
		return inviteDate;
	}
	public void setInviteDate(Date inviteDate) {
		this.inviteDate = inviteDate;
	}
	public String getInviter() {
		return inviter;
	}
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
	public String getInvitee() {
		return invitee;
	}
	public void setInvitee(String invitee) {
		this.invitee = invitee;
	}
	@Override
	public String toString() {
		return "InviteVO [ino=" + ino + ", wcode=" + wcode + ", inviteDate=" + inviteDate + ", inviter=" + inviter
				+ ", invitee=" + invitee + "]";
	}
	
	
}
