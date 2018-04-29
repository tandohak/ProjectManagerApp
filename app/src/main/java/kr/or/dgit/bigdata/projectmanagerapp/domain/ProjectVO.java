package kr.or.dgit.bigdata.projectmanagerapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ProjectVO implements Parcelable {
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

	public ProjectVO() {}

	public ProjectVO(int pno, String wcode, String title, int maker, String explanation, boolean visibility, Date regDate, Date startDate, Date endDate, Date finishDate, int status, int authority, boolean locker) {
		this.pno = pno;
		this.wcode = wcode;
		this.title = title;
		this.maker = maker;
		this.explanation = explanation;
		this.visibility = visibility;
		this.regDate = regDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.finishDate = finishDate;
		this.status = status;
		this.authority = authority;
		this.locker = locker;
	}

	protected ProjectVO(Parcel in) {
		final ClassLoader cl = getClass().getClassLoader();

		pno = in.readInt();
		wcode = in.readString();
		title = in.readString();
		maker = in.readInt();
		explanation = in.readString();
		visibility = in.readByte() != 0;
		regDate = (Date) in.readValue(cl);
		startDate = (Date) in.readValue(cl);
		endDate = (Date) in.readValue(cl);
		finishDate = (Date) in.readValue(cl);
		status = in.readInt();
		authority = in.readInt();
		locker = in.readByte() != 0;
	}

	public static final Creator<ProjectVO> CREATOR = new Creator<ProjectVO>() {
		@Override
		public ProjectVO createFromParcel(Parcel in) {
			return new ProjectVO(in);
		}

		@Override
		public ProjectVO[] newArray(int size) {
			return new ProjectVO[size];
		}
	};

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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.pno);
		dest.writeString(this.wcode);
		dest.writeString(this.title);
		dest.writeString(this.explanation);
		dest.writeValue(this.visibility);
		dest.writeValue(this.regDate);
		dest.writeValue(this.startDate);
		dest.writeValue(this.endDate);
		dest.writeValue(this.finishDate);
		dest.writeInt(this.status);
		dest.writeInt(this.authority);
		dest.writeValue(this.locker);
	}
}
