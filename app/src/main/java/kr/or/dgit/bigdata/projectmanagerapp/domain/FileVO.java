package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class FileVO {
	private int fno;
	private int taskno;
	private int uno;
	private String uploader;
	private String filepath;
	private Date uplaodTime;
	private boolean fix;
	private int type;
	private String fileSize;

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	public int getTaskno() {
		return taskno;
	}

	public void setTaskno(int taskno) {
		this.taskno = taskno;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Date getUplaodTime() {
		return uplaodTime;
	}

	public void setUplaodTime(Date uplaodTime) {
		this.uplaodTime = uplaodTime;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileVO [fno=" + fno + ", taskno=" + taskno + ", uno=" + uno + ", uploader=" + uploader + ", filepath="
				+ filepath + ", uplaodTime=" + uplaodTime + ", fix=" + fix + ", type=" + type + ", fileSize=" + fileSize
				+ "]";
	}
	
}
