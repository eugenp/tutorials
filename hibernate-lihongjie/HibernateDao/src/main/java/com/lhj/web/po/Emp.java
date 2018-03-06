package com.lhj.web.po;

import java.util.Date;

/**
 * Emp entity 
 */

public class Emp implements java.io.Serializable {

	// Fields

	private Integer empno;
	private String ename;
	private String job;
	private Date hiredate;
	private String sex;
	private Date birthday;
	private String address;
	private String phone;
	private String bankcard;
	private String email;
	private Dept dept;
	
	public Emp() {
		super();
	}

	public Emp(Integer empno, Dept dept, String ename, String job,
			Date hiredate, String sex, Date birthday, String address,
			String phone, String bankcard, String email) {
		super();
		this.empno = empno;
		this.dept = dept;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.phone = phone;
		this.bankcard = bankcard;
		this.email = email;
	}

	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}