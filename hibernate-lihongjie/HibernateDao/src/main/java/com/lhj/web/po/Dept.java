package com.lhj.web.po;

import java.util.HashSet;
import java.util.Set;

/**
 * Dept entity
 *
 */

public class Dept implements java.io.Serializable {

	// Fields

	private Integer deptno;
	private String dname;
	private String manager;
	private String phonenumber;
	private Integer peoplenumber;
	
	private Set emps = new HashSet(0);

	public Dept() {
		super();
	}
	
	public Dept(Integer deptno, String dname, String manager,
			String phonenumber, Integer peoplenumber, Set emps) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.manager = manager;
		this.phonenumber = phonenumber;
		this.peoplenumber = peoplenumber;
		this.emps = emps;
	}

	public Integer getDeptno() {
		return deptno;
	}
	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Set getEmps() {
		return emps;
	}
	public void setEmps(Set emps) {
		this.emps = emps;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public Integer getPeoplenumber() {
		return peoplenumber;
	}
	public void setPeoplenumber(Integer peoplenumber) {
		this.peoplenumber = peoplenumber;
	}


}