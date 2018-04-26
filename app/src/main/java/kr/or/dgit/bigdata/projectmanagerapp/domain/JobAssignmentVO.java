package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class JobAssignmentVO {
	private int massno;
	private int taskno;

	public int getMassno() {
		return massno;
	}

	public void setMassno(int massno) {
		this.massno = massno;
	}

	public int getTaskno() {
		return taskno;
	}

	public void setTaskno(int taskno) {
		this.taskno = taskno;
	}

	@Override
	public String toString() {
		return "JobAssignmentVO [massno=" + massno + ", taskno=" + taskno + "]";
	}

}
