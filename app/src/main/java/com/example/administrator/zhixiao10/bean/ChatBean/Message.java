package com.example.administrator.zhixiao10.bean.ChatBean;

import com.example.administrator.zhixiao10.utils.MyTime;

/**
 * 由自定义协议设计的通讯消息类
 *
 */
public class Message extends ProtocalObj{
    public String type = MessageType.MSG_TYPE_CHAT_P2P;// 类型的数据 chat login
    public String from = 0+"";// 发送者 account
    public String fromNick = "";// 昵称
    public int fromAvatar = 1;// 头像
    public String to = 0+""; // 接收者 account
    public String content = ""; // 消息的内容 约不?
    public String sendTime = MyTime.getTime(); // 发送时间

}
