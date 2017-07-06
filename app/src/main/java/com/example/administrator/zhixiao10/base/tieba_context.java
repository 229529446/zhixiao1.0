package com.example.administrator.zhixiao10.base;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.zhixiao10.Activity.ChatActivity;
import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.R;

/**
 * Created by Administrator on 2017/6/16.
 */
public class tieba_context {

    public MainActivity mActivity;

    public View getmRootView() {
        return mRootView;
    }

    private View mRootView;

    private LinearLayout chatroom;

    public tieba_context(MainActivity mActivity) {
        this.mActivity = mActivity;
        initView();
        initData();


    }

    private void initData() {
        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                mActivity.startActivity(intent);

            }
        });
    }

    private void initView() {
        mRootView = View.inflate(mActivity, R.layout.tieba_context_layout, null);
        chatroom = (LinearLayout) mRootView.findViewById(R.id.chat_room);

    }
}
