package com.example.administrator.zhixiao10.bean.ChatBean;

import com.thoughtworks.xstream.XStream;

/**
 * 定义实体共有的
 */
public class ProtocalObj {

    /**
     * 实体生成xml
     *
     * @return
     */

    public String toXML(){
        XStream x = new XStream();
        x.alias(getClass().getSimpleName(),getClass());//设置别名，默认是类的全路径名
        String xml = x.toXML(this);
        return xml;

    }

    /**
     * xml 生成实体类
     * @param xml
     * @return
     */
    public Object fromXML(String xml){
        XStream x = new XStream();
        x.alias(getClass().getSimpleName(),getClass());
        return x.fromXML(xml);

    }




}
