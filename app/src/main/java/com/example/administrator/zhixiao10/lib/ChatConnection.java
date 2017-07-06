package com.example.administrator.zhixiao10.lib;

import android.util.Log;

import com.example.administrator.zhixiao10.bean.ChatBean.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 连接类：连接、断开、发送、接收
 */
public class ChatConnection {

    private String host = "";
    private int port = 1475;
    Socket client;
    private DataInputStream reader;
    private DataOutputStream writer;
    private WaitThread waitThread;
    public boolean isWaiting;

    /**
     *
     * @param host 端口
     * @param port 远程主机地址
     */

    public ChatConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }


    /**
     * 开启连接
     * @throws UnknownHostException
     * @throws IOException
     */
    public void connect()throws UnknownHostException, IOException {

        //创建连接
        client = new Socket(host,port);
        reader = new DataInputStream(client.getInputStream());
        writer = new DataOutputStream(client.getOutputStream());

        //开启消息等待线程
        isWaiting = true;
        waitThread = new WaitThread();
        waitThread.start();


    }

    /**
     * 断开连接
     *
     * @throws IOException
     */
    public void disConnect() throws IOException {
        // 关闭连接就是释放资源
        client.close();
        reader.close();
        writer.close();
        isWaiting = false;
    }

    /**
     * 发送xml格式消息
     * @param xml
     * @throws IOException
     */
    public void  sendMessage(String xml) throws IOException {
        writer.writeUTF(xml);
    }

    /**
     * 发送java实体
     * @param message
     * @throws IOException
     */

    public void sendMessage(Message message) throws IOException {
        writer.writeUTF(message.toXML());
    }




    /**
     *
     * 等待接收消息
     */

    private class WaitThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (isWaiting){
                try {
                    //获取消息
                    String xml = reader.readUTF();
                    Message msg = new Message();
                    msg = (Message) msg.fromXML(xml);
                    Log.i("message：", "收到消息: "+msg);

                    for (OnMessageListener listener : listeners){
                        listener.onReveive(msg);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }



    // 服务器会经常给客户端发送消息，客户端会有不同的消息到来，所以新建一个监听器的集合，往集合中添加一个监听器就调用一次onReveive方法，

	/*
	 * 集合中有就调用，集合中没有就不调用
	 */
    private List<OnMessageListener> listeners = new ArrayList<OnMessageListener>();

    public void addOnMessageListener(OnMessageListener listener) {
        listeners.add(listener);
    }

    public void removeOnMessageListener(OnMessageListener listener) {
        listeners.remove(listener);
    }


    /**
     *消息监听接口
     *
     */
    public static interface OnMessageListener {
       public void onReveive(Message msg);
    }


}
