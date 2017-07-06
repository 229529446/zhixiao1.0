package com.example.administrator.zhixiao10.bean;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
/**
 * Created by Administrator on 2017/1/13.
 */
public class passage {
    private String pid;
    private String purl;
    private String peditor;
    private String ptime;
    private String ptitle;
    private String pcon;

    public String getPcon() {
        return pcon;
    }
    public void setPcon(String pcon) {
        this.pcon = pcon;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getPurl() {
        return purl;
    }
    public void setPurl(String purl) {
        this.purl = purl;
    }
    public String getPeditor() {
        return peditor;
    }
    public void setPeditor(String peditor) {
        this.peditor = peditor;
    }
    public String getPtime() {
        return ptime;
    }
    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
    public String getPtitle() {
        return ptitle;
    }
    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }
    @Override
    public String toString() {
        return "passage [pid=" + pid + ", purl=" + purl + ", peditor="
                + peditor + ", ptime=" + ptime + ", ptitle=" + ptitle
                + ", pcon=" + pcon + "]";
    }

}
