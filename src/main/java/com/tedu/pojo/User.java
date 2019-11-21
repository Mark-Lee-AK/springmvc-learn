package com.tedu.pojo;

public class User {
	private String name;
	private int age;
	private String addr;
	
	public User() {
		System.out.println("用无参构造创建了一个User对象");
	}
	public User(String name, int age, String addr) {
		this.name = name;
		this.age = age;
		this.addr = addr;
		System.out.println("用含参构造创建了一个User对象");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", addr=" + addr + "]";
	}
}
