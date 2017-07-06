package com.example.administrator.zhixiao10.bean;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class User {

    private String ID;
    private  String Password;
    private String PhoneNum;
    private String UserName;
    private int Sex;
    private String Address;


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }


    public String getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int sex) {
        Sex = sex;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", Password='" + Password + '\'' +
                ", PhoneNum='" + PhoneNum + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Sex=" + Sex +
                ", Address='" + Address + '\'' +
                '}';
    }
}
