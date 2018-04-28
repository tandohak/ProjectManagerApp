package kr.or.dgit.bigdata.projectmanagerapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class WorkspaceVO implements Parcelable {
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

	protected WorkspaceVO(Parcel in) {
		final ClassLoader cl = getClass().getClassLoader();

		wcode = in.readString();
		name = in.readString();
		maker = in.readString();
		uno = in.readInt();
		regDate = (Date) in.readValue(cl);
	}

	public static final Creator<WorkspaceVO> CREATOR = new Creator<WorkspaceVO>() {
		@Override
		public WorkspaceVO createFromParcel(Parcel in) {
			return new WorkspaceVO(in);
		}

		@Override
		public WorkspaceVO[] newArray(int size) {
			return new WorkspaceVO[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.wcode);
		dest.writeString(this.name);
		dest.writeString(this.maker);
		dest.writeInt(this.uno);
		dest.writeValue(this.regDate);
	}
}
