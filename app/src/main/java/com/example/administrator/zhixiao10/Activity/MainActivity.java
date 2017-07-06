package com.example.administrator.zhixiao10.Activity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.ChatBean.Message;
import com.example.administrator.zhixiao10.bean.ChatBean.MessageType;
import com.example.administrator.zhixiao10.fragments.ContentFragment;
import com.example.administrator.zhixiao10.fragments.LeftFragment;
import com.example.administrator.zhixiao10.lib.ChatConnection;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.utils.ThreadUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.IOException;

public class MainActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";
    private static SlidingMenu slidingMenu;

    public String id;
    public String pwd;

    private ChatConnection chatConnection;

    private LeftFragment leftFragment;
    private ContentFragment contentFragment;

    public LeftFragment getLeftFragment() {
        return leftFragment;
    }

    public ContentFragment getContentFragment() {
        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_fragment_layout);





        //设置侧边栏布局
        setBehindContentView(R.layout.left_fragment_layout);
        //获取侧边栏对象
        slidingMenu = getSlidingMenu();
//        //设置划出方向
//        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置全屏触摸
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置预留屏幕宽度
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int w = (int) (width * 0.8);
        slidingMenu.setBehindWidth(w);


        id = PrefUtils.getAccount(this, "AccountID", "");
        pwd = PrefUtils.getAccount(this, "AccountPwd", "");

           /*
         * 登录的逻辑:首先连接服务器，获取用户输入的账号和密码，讲账户和密码发送给服务器;服务器做一些验证操作，将验证结果返回给客户端，
		 * 客户端再进行接收消息的操作
		 */
        // 与网络相关的操作要放在子线程中进行
        ThreadUtils.runInSubThread(new Runnable() {

            public void run() {
                try {
                    chatConnection = new ChatConnection(ConnectionUrl.CHAT_SERVICE_URL, 1475);
                    chatConnection.connect();// 建立连接
                    // 建立连接之后，将监听器添加到连接里面
                    chatConnection.addOnMessageListener(listener);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("chatConn", "聊天连接失败");
                }

                try {
                    Message msg = new Message();
                    msg.type = MessageType.MSG_TYPE_LOGIN;
                    msg.content = id + "#" + pwd;
                    chatConnection.sendMessage(msg);
                    Log.i("chatConn", "登录中");

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


//        //登录聊天服务
//        ThreadUtils.runInSubThread(new Runnable() {
//
//            public void run() {
//                try {
//                    Message msg = new Message();
//                    msg.type = MessageType.MSG_TYPE_LOGIN;
//                    msg.content = id + "#" + pwd;
//                    chatConnection.sendMessage(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("mainActivity", "onResume: " + "执行");
        leftFragment = new LeftFragment();
        contentFragment = new ContentFragment();
        initFragment();

    }

    private void initFragment() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();


        FragmentTransaction ft = fm.beginTransaction();
        //替换成自己定义的
        ft.replace(R.id.left_fragment_layout, leftFragment, FRAGMENT_LEFT_MENU);
        ft.replace(R.id.main_fragment_layout, contentFragment, FRAGMENT_CONTENT);

        ft.commit();
    }

    public static void ShowMenu() {
        slidingMenu.showMenu();
    }

    private static void ShowCon(){
        slidingMenu.showContent();
    }




    private ChatConnection.OnMessageListener listener = new ChatConnection.OnMessageListener() {
        @Override
        public void onReveive(final Message msg) {
            System.out.println(msg.toXML());

            ThreadUtils.runInUiThread(new Runnable() {
                @Override
                public void run() {
                    if (MessageType.MSG_TYPE_BUDDY_LIST.equals(msg.type)) {
                        // 登录成功，返回的数据是好友列表
                        zhixiaoApp app = (zhixiaoApp) getApplication();
                        // 保存一个长连接
                        app.setMyConn(chatConnection);
                        // 保存当前登录用户的账号
                        app.setMyAccount(id);

                        android.util.Log.i("chatLogin", "聊天服务登录成功! ");

                    } else {
                        android.util.Log.i("chatLogin", "聊天服务登录失败! ");
                    }


                }
            });

        }
    };

}
