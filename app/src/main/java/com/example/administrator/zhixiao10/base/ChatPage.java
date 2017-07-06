package com.example.administrator.zhixiao10.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.zhixiaoApp;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.bean.ChatBean.Message;
import com.example.administrator.zhixiao10.bean.ChatBean.MessageType;
import com.example.administrator.zhixiao10.lib.ChatConnection;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.utils.ThreadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class ChatPage {
    public Activity mActivity;
    private ListView ChatList;
    private EditText input;
    private Button send;


    private zhixiaoApp app;
    private ChartMessageAdapter adapter;
    // 这是点击的用户，也就是你要发消息给谁
    private String toNick;// 要发送给谁
//    private String toAccount;// 要发送的账号
    private String fromAccount;// 我的账号，我要跟谁睡聊天
    private String inputStr;// 聊天内容
    // 聊天消息的集合
    private ArrayList<Message> messages = new ArrayList<Message>();




    public View getmRootView() {
        return mRootView;
    }
    private View mRootView;

    public ChatPage(Activity mActivity) {
        this.mActivity = mActivity;

        initView();
        initData();
    }




    /**
     * 接收消息，使用监听器
     */
    private ChatConnection.OnMessageListener listener = new ChatConnection.OnMessageListener() {

        public void onReveive(final Message msg) {
            // 注意onReveive是子线程，更新界面一定要在主线程中
            ThreadUtils.runInUiThread(new Runnable() {

                public void run() {

                    // 服务器返回回来的消息
                    System.out.println(msg.content);


                    if (MessageType.MSG_TYPE_CHAT_ROOM.equals(msg.type)) {
                        messages.add(msg);// 把消息加到消息集合中，这是最新的消息

                        PrefUtils.setRecord(mActivity,"record",messages);

                        // 刷新消息
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        // 展示到最新发送的消息中
                        if (messages.size() > 0) {
                            ChatList.setSelection(messages.size() - 1);
                        }

                    }

                }
            });

        }
    };



    private void initData() {

        app = (zhixiaoApp) mActivity.getApplication();
        messages = PrefUtils.getRecord(mActivity,"record",null);
        Log.i("chatdata", messages.toString());

        app.getMyConn().addOnMessageListener(listener);
        fromAccount = app.getMyAccount();// 我的账户
        adapter = new ChartMessageAdapter(mActivity, messages);
        ChatList.setAdapter(adapter);


        // 发送消息
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputStr = input.getText().toString().trim();
                // 清空输入框
                input.setText("");
                final Message msg = new Message();
                if ("".equals(inputStr)) {
                    Toast.makeText(mActivity, "不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                msg.type = MessageType.MSG_TYPE_CHAT_ROOM;
                msg.from = fromAccount;
//                msg.to = toAccount;
                msg.content = inputStr;
//                msg.fromAvatar = R.drawable.ic_launcher;


                messages.add(msg);
                PrefUtils.setRecord(mActivity,"record",messages);

                // 数据集合有了，创建适配器
                // 刷新消息
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                // 展示到最新发送的消息处
                if (messages.size() > 0) {
                    ChatList.setSelection(messages.size() - 1);
                }

                // 发送消息到服务器--子线程
                ThreadUtils.runInSubThread(new Runnable() {

                    public void run() {
                        try {
                            app.getMyConn().sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });


    }

    private void initView() {

        mRootView = View.inflate(mActivity, R.layout.chat_page_layout, null);
        ChatList = (ListView) mRootView.findViewById(R.id.chat_list);
        input = (EditText) mRootView.findViewById(R.id.input);
        send = (Button) mRootView.findViewById(R.id.send);


    }


    /**
     * 聊天列表适配器
     */

    public class ChartMessageAdapter extends ArrayAdapter<Message> {
        zhixiaoApp app;
        List<Message> data = new ArrayList<Message>();



        public ChartMessageAdapter(Context context, List<Message> objects) {
            super(context, 0, objects);
            data = objects;
            Activity activity = (Activity) context;
            app = (zhixiaoApp) activity.getApplication();

        }

        @Override
        public Message getItem(int position) {
            return data.get(position);
        }

        /**
         * 根据集合中的position位置，返回不同的值，代表不同的布局 0代表自己发送的消息 1代表接收到的消息
         *
         */
        @Override
        public int getItemViewType(int position) {// 这个方法是给getView用得
            Message msg = getItem(position);
            // 消息来自谁，如果消息来自我自己，说明是我发送的
            if (msg.from.equals(app.getMyAccount()) ) {
                // 我自己的消息，发送
                return 0;
            } else {
                return 1;
            }
        }

        /**
         * 两种布局
         */
        @Override
        public int getViewTypeCount() {

            return 2;
        }

        class ViewHolder {
            TextView time;
            TextView content;
            ImageView head;
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);
            if (0 == type) {
                // 发送的布局
                ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(getContext(),
                            R.layout.item_chat_send, null);
                    holder = new ViewHolder();
                    holder.time = (TextView) convertView.findViewById(R.id.time);
                    holder.content = (TextView) convertView
                            .findViewById(R.id.content);
                    holder.head = (ImageView) convertView.findViewById(R.id.head);
                    holder.name = (TextView) convertView.findViewById(R.id.account_fromnick);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                // 设置值
                Message msg = getItem(position);
                holder.time.setText(msg.sendTime);
//                holder.head.setImageResource(msg.fromAvatar);
                holder.content.setText(msg.content);
                holder.name.setText(msg.from);
                return convertView;

            } else {
                // 接收的布局
                ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(getContext(),
                            R.layout.item_chat_receive, null);
                    holder = new ViewHolder();
                    holder.time = (TextView) convertView.findViewById(R.id.time);
                    holder.content = (TextView) convertView
                            .findViewById(R.id.content);
                    holder.head = (ImageView) convertView.findViewById(R.id.head);
                    holder.name = (TextView) convertView.findViewById(R.id.account_fromnick);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                // 设置值
                Message msg = getItem(position);
//                holder.head.setImageResource(msg.fromAvatar);
                holder.time.setText(msg.sendTime);
                holder.content.setText(msg.content);
                holder.name.setText(msg.from);

                return convertView;
            }

        }
    }




}
