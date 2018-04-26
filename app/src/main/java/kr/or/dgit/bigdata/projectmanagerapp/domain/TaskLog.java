package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class TaskLog {
	private int taskno;
	private String log;
	public int getTaskno() {
		return taskno;
	}
	public void setTaskno(int taskno) {
		this.taskno = taskno;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	@Override
	public String toString() {
		return "TaskLog [taskno=" + taskno + ", log=" + log + "]";
	}
	
}
