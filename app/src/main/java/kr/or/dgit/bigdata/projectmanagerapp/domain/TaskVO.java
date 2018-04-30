package kr.or.dgit.bigdata.projectmanagerapp.domain;

import java.util.Date;

public class TaskVO {
	private int taskno;
	private int tlno;
	private int massno;
	private String explanation;
	private String taskname;
	private Date regDate;
	private Date startDate;
	private Date endDate;
	private Date finishDate;
	private Date workingtime;
	private int cycle;
	private String writer;
	private String colorLabel;
	private int status;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TaskVO taskVO = (TaskVO) o;

		return taskno == taskVO.taskno;
	}

	@Override
	public int hashCode() {
		return taskno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}  

	public int getTaskno() {
		return taskno;
	}

	public void setTaskno(int taskno) {
		this.taskno = taskno;
	}

	public int getTlno() {
		return tlno;
	}

	public void setTlno(int tlno) {
		this.tlno = tlno;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getTaskname() {
		return taskname;
	}

	public int getMassno() {
		return massno;
	} 

	public void setMassno(int massno) {
		this.massno = massno;
	}
 
	public void setTaskname(String taskname) {
		this.taskname = taskname; 
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

	public Date getWorkingtime() {
		return workingtime;
	}

	public void setWorkingtime(Date workingtime) {
		this.workingtime = workingtime;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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

	public String getColorLabel() {
		return colorLabel;
	}

	public void setColorLabel(String colorLabel) {
		this.colorLabel = colorLabel;
	}

	@Override
	public String toString() {
		return "TaskVO [taskno=" + taskno + ", tlno=" + tlno + ", explanation=" + explanation + ", taskname=" + taskname
				+ ", regDate=" + regDate + ", startDate=" + startDate + ", endDate=" + endDate + ", workingtime="
				+ workingtime + ", cycle=" + cycle + ", writer=" + writer + "]";
	}

}
