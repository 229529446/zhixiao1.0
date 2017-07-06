package com.example.administrator.zhixiao10.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.fragments.loginFragment;
import com.example.administrator.zhixiao10.fragments.registerFragment;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends FragmentActivity implements View.OnClickListener {


    private NoScrollViewPager mViewpager;
    private List<Fragment> fragmentList;
    private loginFragment loginFragment;
    private registerFragment registerFragment;


    private Button LoginBtn;
    private Button RegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (PrefUtils.getAccount(this, "AccountID", null) == null) {
            setContentView(R.layout.welcome);

            Log.i("account", "onCreate: "+PrefUtils.getAccount(this, "AccountID", null));
            mViewpager = (NoScrollViewPager) findViewById(R.id.denluzhuce);
            LoginBtn = (Button) findViewById(R.id.login_page_btn);
            RegisterBtn = (Button) findViewById(R.id.register_page_btn);


            loginFragment = new loginFragment();
            registerFragment = new registerFragment();
            fragmentList = new ArrayList<Fragment>();

            fragmentList.add(loginFragment);
            fragmentList.add(registerFragment);

            mViewpager.setAdapter(new myAdapter(getSupportFragmentManager()));

            LoginBtn.setOnClickListener(this);
            RegisterBtn.setOnClickListener(this);
            changBtnBackground(LoginBtn);

            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int w = (int) (wm.getDefaultDisplay().getWidth() * 0.7);
            int h = (int) (wm.getDefaultDisplay().getHeight() * 0.7);

            LinearLayout width_l = (LinearLayout) findViewById(R.id.width), height_l = (LinearLayout) findViewById(R.id.height);

            ViewGroup.LayoutParams lpw;
            lpw = width_l.getLayoutParams();
            lpw.width = w;
            lpw.height = h;
            width_l.setLayoutParams(lpw);


            ViewGroup.LayoutParams lph;
            lph = height_l.getLayoutParams();
            lph.width = w;
            lph.height = h;
            height_l.setLayoutParams(lph);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_page_btn:
                changBtnBackground(LoginBtn);
                mViewpager.setCurrentItem(0);
                break;
            case R.id.register_page_btn:
                changBtnBackground(RegisterBtn);
                mViewpager.setCurrentItem(1);
                break;
        }

    }

    public void changPageInLogin() {
        changBtnBackground(LoginBtn);
        mViewpager.setCurrentItem(0);
    }


    /**
     * 改变选中按钮的背景色
     *
     * @param selectBtn
     */
    public void changBtnBackground(Button selectBtn) {

        LoginBtn.setBackgroundColor(Color.parseColor("#00000000"));
        RegisterBtn.setBackgroundColor(Color.parseColor("#00000000"));

        selectBtn.setBackgroundColor(Color.parseColor("#f1ffffff"));

    }


    public class myAdapter extends FragmentStatePagerAdapter {

        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
