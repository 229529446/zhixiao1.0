package com.example.administrator.zhixiao10.Activity;


import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.base.ChatPage;

public class ChatActivity extends FragmentActivity {

    FrameLayout fl;
    ImageButton BackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fl = (FrameLayout) findViewById(R.id.chat_frag);
        BackBtn = (ImageButton)findViewById(R.id.back_btn);

        ChatPage chatPage = new ChatPage(this);
        fl.addView(chatPage.getmRootView());

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
