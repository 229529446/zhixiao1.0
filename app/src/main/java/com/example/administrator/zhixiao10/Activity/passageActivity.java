package com.example.administrator.zhixiao10.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.passage;
import com.example.administrator.zhixiao10.fragments.CommentFragment;
import com.example.administrator.zhixiao10.fragments.ContentFragment;
import com.example.administrator.zhixiao10.fragments.LeftFragment;
import com.example.administrator.zhixiao10.fragments.PassageFragment;
import com.example.administrator.zhixiao10.view.RefreshListView;
import com.example.administrator.zhixiao10.view.ScrollViewPager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class passageActivity extends AppCompatActivity {

    //    private TextView tv,conTitle,conTime;
//    private passage ps;
//    private ImageView titleImage;
//    private BitmapUtils bitmapUtils;
//    private Toolbar toolbar;
//
//
//    private static SlidingMenu slidingMenu;
//
//    private static final String FRAGMENT_COMMENT = "fragment_comment";
//    private static final String FRAGMENT_PASSAGE = "fragment_passage";
//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    private GoogleApiClient client;



    private ScrollViewPager viewPager;
    private List<Fragment> fragmentList;
    private CommentFragment commentFragment;
    private PassageFragment passageFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_passage);
        String title = getIntent().getStringExtra("title");
        viewPager = (ScrollViewPager) findViewById(R.id.passage_viewpager);
        commentFragment = new CommentFragment();
        passageFragment = new PassageFragment();
        fragmentList = new ArrayList<Fragment>();


        fragmentList.add(passageFragment);
        fragmentList.add(commentFragment);


        viewPager.setAdapter(new myAdapter(getSupportFragmentManager()));








////        toolbar = (Toolbar) findViewById(R.id.toolbar);
////        toolbar.setTitle(title);
//
//        //***************************************//
//
//        //设置侧边栏布局
//        setBehindContentView(R.layout.left_fragment_layout);
//        //获取侧边栏对象
//        slidingMenu = getSlidingMenu();
//        //设置划出方向
//        slidingMenu.setMode(SlidingMenu.RIGHT);
//        //设置全屏触摸
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        //设置预留屏幕宽度
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int w = (int) (width);
//        slidingMenu.setBehindWidth(w);
//
//        initFragment();


        //*************************************//


//        tv = (TextView) findViewById(R.id.tv_con);
//        conTitle = (TextView) findViewById(R.id.con_title);
//        conTime = (TextView) findViewById(R.id.con_time);
//        titleImage = (ImageView) findViewById(R.id.title_image);
//
//        bitmapUtils = new BitmapUtils(this);
//        bitmapUtils.configDefaultLoadingImage(R.drawable.bagg);
//
//        String pid = getIntent().getStringExtra("pid");


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(1);
//            }
//        });


//        RequestParams requestParams = new RequestParams();
//        requestParams.addBodyParameter("pid", pid);
//        HttpUtils http = new HttpUtils();
//        http.configCurrentHttpCacheExpiry(1000 * 10);
//        http.send(HttpRequest.HttpMethod.POST, ConnectionUrl.SERVICE_URL + "getPassageByPid", requestParams, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//
//                JsonParser parser = new JsonParser();
//                JsonElement element = parser.parse(responseInfo.result.toString());
//                JsonObject root = element.getAsJsonObject();
//
//
//                JsonPrimitive code = root.getAsJsonPrimitive("code");
//
//                if (code.getAsString().equals("200")) {
//                    JsonObject data = root.getAsJsonObject("data");
//
//                    ps = new Gson().fromJson(data, new TypeToken<passage>(){}.getType());
//
//                    Log.i("jj", "onSuccess: data========" + ps);
//
//                    tv.setText(ps.getPcon());
//
//                    conTitle.setText(ps.getPtitle());
//
//                    conTime.setText(ps.getPtime());
//
//                    bitmapUtils.display(titleImage,ConnectionUrl.ROOT_URL+"PassageImgs/"+ps.getPurl()+".jpg");
//
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException error, String msg) {
//
//            }
//        });


//        wv = (WebView) findViewById(R.id.webView01);
//        wv.loadUrl();


    }


//    private void initFragment() {
//        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//
//        FragmentTransaction ft = fm.beginTransaction();
//        //替换成自己定义的
//        ft.replace(R.id.left_fragment_layout, new CommentFragment(), FRAGMENT_COMMENT);
//        ft.replace(R.id.passage_fragment, new PassageFragment(), FRAGMENT_PASSAGE);
//        ft.commit();
//    }




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



    public void changPage(int position){
        viewPager.setCurrentItem(position);
    }



    public CommentFragment getCommentFragment(){
        return commentFragment;
    }
}
