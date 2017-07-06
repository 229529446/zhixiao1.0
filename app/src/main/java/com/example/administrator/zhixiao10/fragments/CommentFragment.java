package com.example.administrator.zhixiao10.fragments;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.Activity.passageActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.base.popuCon;
import com.example.administrator.zhixiao10.bean.comment;
import com.example.administrator.zhixiao10.bean.passage;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.view.MyPopupwindow;
import com.example.administrator.zhixiao10.view.RefreshListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */




public class CommentFragment extends BaseFragment{

    private View view;

    private List<comment> List;
    private  newListAdatpter newListAdatpter;
    private int page = 1;
    private String pid;
    private ImageButton BackBtn;

    private RefreshListView listView;

    private Button show;

    private TextView conTitle, conTime;

    private passage ps;


    private MyPopupwindow popupwindow;

    public MyPopupwindow getPopupwindow() {
        return popupwindow;
    }


    @Override
    public View initViews() {
        pid = mActivity.getIntent().getStringExtra("pid");

       view =  View.inflate(mActivity, R.layout.comment_layout, null);
        conTitle = (TextView) view.findViewById(R.id.con_title);
        conTime = (TextView) view.findViewById(R.id.con_time);
        conTitle.setText(mActivity.getIntent().getStringExtra("title"));
        conTime.setText(mActivity.getIntent().getStringExtra("time"));

        BackBtn = (ImageButton) view.findViewById(R.id.back_btn);
        listView = (RefreshListView) view.findViewById(R.id.comment_list);

        show = (Button) view.findViewById(R.id.show_popu);
        popuCon popuCon = new popuCon(mActivity,pid);
        popupwindow = new MyPopupwindow(getActivity(),popuCon.getmRootView());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            }
        });

        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passageActivity passageActivity = (passageActivity) mActivity;
                passageActivity.changPage(0);

            }
        });

        return view;
    }




    @Override
    public void initData() {
        super.initData();


        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pid",pid);
        requestParams.addBodyParameter("page",""+page);


        HttpUtils httpUtils = new HttpUtils();
        httpUtils.configCurrentHttpCacheExpiry(1000*5);

        httpUtils.send(HttpRequest.HttpMethod.POST, ConnectionUrl.SERVICE_URL + "getCommentListByPid",requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                listView.onRefreshComplete(true);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseInfo.result.toString());
                JsonObject root = element.getAsJsonObject();
                JsonPrimitive code = root.getAsJsonPrimitive("code");
                initListView(root,code);
            }
            @Override
            public void onFailure(HttpException error, String msg) {
                listView.onRefreshComplete(false);
                Toast.makeText(getActivity(),"评论加载失败！！",Toast.LENGTH_SHORT).show();
            }
        });





    }




    private void initListView(JsonObject root, JsonPrimitive code){

        if(code.getAsString().equals("200")){
            JsonArray data = root.getAsJsonArray("data");


            if(!data.toString().equals("[]")){
                if (page == 1){  //第一次进入
                    List = new Gson().fromJson(data,new TypeToken<List<comment>>(){}.getType());
                    newListAdatpter = new newListAdatpter();
                    listView.setAdapter(newListAdatpter);

                    //设置下拉刷新,加载更多监听
                    listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            page = 1;
                            initData();
                            newListAdatpter.notifyDataSetChanged();
                        }

                        @Override
                        public void onLoadMore() {
                            page++;
                            initData();
                        }
                    });
                }else {//加载更多
                    List<comment> pageList = new Gson().fromJson(data,new TypeToken<List<comment>>(){}.getType());
                    List.addAll(pageList);
                    newListAdatpter.notifyDataSetChanged();
                }


            }else {
                Toast.makeText(getActivity(),"没有更多了！",Toast.LENGTH_LONG).show();
            }

        }
    }




    public class newListAdatpter extends BaseAdapter {


        @Override
        public int getCount() {
            return List.size();
        }

        @Override
        public Object getItem(int position) {
            return List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHoder viewHoder;
            if (convertView==null){
                convertView = View.inflate(getContext(),R.layout.comment_item_layout,null);
                viewHoder = new ViewHoder();
                viewHoder.imgeP = (ImageView) convertView.findViewById(R.id.comment_image);
                viewHoder.tvTime = (TextView) convertView.findViewById(R.id.comment_time);
                viewHoder.con = (TextView) convertView.findViewById(R.id.comment_con);
                viewHoder.commentName = (TextView) convertView.findViewById(R.id.comment_name);
                convertView.setTag(viewHoder);
            }else {
                viewHoder = (ViewHoder) convertView.getTag();
            }

            comment comment = (comment) getItem(position);
            BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
            viewHoder.con.setText(comment.getCon());
            viewHoder.tvTime.setText(comment.getCtime());
            viewHoder.commentName.setText(comment.getCer());
            bitmapUtils.configDefaultLoadingImage(R.drawable.bagg);
//            bitmapUtils.display(viewHoder.imgeP, ConnectionUrl.ROOT_URL+"PassageImgs/"+ps.getPurl()+".jpg");
//            String ids = PrefUtils.getString(getActivity(),"read_ids","");
//
//
//            if (ids.contains(((passage) getItem(position)).getPid())){
//                viewHoder.con.setTextColor(Color.DKGRAY);
//            }else {
//                viewHoder.con.setTextColor(Color.BLACK);
//            }

            return convertView;
        }
    }

    static  class ViewHoder{
        public  TextView con;
        public TextView tvTime;
        public ImageView imgeP;
        public TextView commentName;
    }
}
