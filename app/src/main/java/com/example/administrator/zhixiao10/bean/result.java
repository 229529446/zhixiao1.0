package com.example.administrator.zhixiao10.bean;

public class result<T> {
	public String code;
	public T data;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setDataT(T dataT) {
		this.data = dataT;
	}
	
	

}
