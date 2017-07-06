package com.example.administrator.zhixiao10.bean;

/**
 * Created by Administrator on 2017/5/28.
 */
public class comment {

    private String cid;
    private String pid;
    private String con;
    private String cer;
    private String ctime;


    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }


    @Override
    public String toString() {
        return "comment{" +
                "cid='" + cid + '\'' +
                ", pid='" + pid + '\'' +
                ", con='" + con + '\'' +
                ", cer='" + cer + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
