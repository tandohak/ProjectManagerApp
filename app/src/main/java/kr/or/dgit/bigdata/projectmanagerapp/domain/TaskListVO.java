package kr.or.dgit.bigdata.projectmanagerapp.domain;

public class TaskListVO {
	private int tlno;
	private int pno;
	private String name;
	private int list_order;
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTlno() {
		return tlno;
	}
	public void setTlno(int tlno) {
		this.tlno = tlno;
	}
	public int getList_order() {
		return list_order;
	}
	public void setList_order(int list_order) {
		this.list_order = list_order;
	}
	@Override
	public String toString() {
		return "TaskListVO [tlno=" + tlno + ", pno=" + pno + ", name=" + name + ", list_order=" + list_order + "]";
	}
	
}
