package com.example.administrator.zhixiao10.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.passageActivity;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.passage;
import com.example.administrator.zhixiao10.lib.ConnManager;
import com.example.administrator.zhixiao10.lib.callback.ObjCallback;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.view.CustomImageView;
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

public class ListFragment extends Fragment {



    private CustomImageView customImageView;
    private TextView ttt;
    private View view;
    private RefreshListView news_lv;
    private boolean isVisible = false;


    private List<passage> List;
    private  newListAdatpter newListAdatpter;
    private int page = 1;

    private BitmapUtils bitmapUtils;


    private String type;

    private Dialog progressDialog;


    public ListFragment(){

    }

    public ListFragment(String type){
        this.type=type;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible=isVisibleToUser;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(),R.layout.news_list,null);
//        if (isVisible){
//            Log.i("jj", "onCreateView: tuijian");
//            initDate("推荐");
//        }

        page = 1;

        progressDialog = new Dialog(getActivity(),R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        progressDialog.show();
        initDate();
        return view;
    }


    @Override
    public void onStart() {

        Log.i("jj", "onStart: "+List);

        super.onStart();
    }

    @Override
    public void onResume() {

        Log.i("jj", "onResume: "+type+List);

        super.onResume();
    }

    @Override

    /*依附的Activity创建完成*/
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void initDate(){
//        customImageView = (CustomImageView) view.findViewById(R.id.test222);
//        ttt = (TextView) view.findViewById(R.id.ttt);
//        ttt.setText(t);
//        BitmapUtils bitmapUtils = new BitmapUtils(getActivity());
//        bitmapUtils.configDefaultLoadingImage(R.drawable.bagg);
//        bitmapUtils.display(customImageView,"http://sese666.com.cn/2.jpg");




//        ConnManager.getInstance(getContext()).sendRequest(ConnectionUrl.SERVICE_URL + "getPassageListByType", requestParams, new ObjCallback<List<passage>>() {
//
//            @Override
//            public void onSuccess(List<passage> data) {
//                List = data;
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });
        Log.i("jj", "initDate: "+type);


        news_lv = (RefreshListView) view.findViewById(R.id.news_lv);

            RequestParams requestParams = new RequestParams();
            requestParams.addBodyParameter("type","1");
            requestParams.addBodyParameter("page",""+page);
            HttpUtils http = new HttpUtils();
            http.configCurrentHttpCacheExpiry(1000*10);





            http.send(HttpRequest.HttpMethod.POST, ConnectionUrl.SERVICE_URL + "getPassageListByType", requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {


                    news_lv.onRefreshComplete(true);
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(responseInfo.result.toString());
                    JsonObject root = element.getAsJsonObject();

                    JsonPrimitive code = root.getAsJsonPrimitive("code");
                    initListView(root,code);

//                    Toast.makeText(getActivity(),"请求完成",Toast.LENGTH_SHORT).show();

                }


                @Override
                public void onFailure(HttpException error, String msg) {
                    news_lv.onRefreshComplete(false);
                    Toast.makeText(getActivity(),"网络请求失败！！",Toast.LENGTH_SHORT).show();
                }
            });






        news_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String ids = PrefUtils.getString(getActivity(),"read_ids","");
                String readId = List.get(position-1).getPid();
                if (!ids.contains(readId)) {
                    ids = ids+readId+",";
                    PrefUtils.setString(getActivity(),"read_ids",ids);
                }

                changeReadState(view);

                Intent intent = new Intent(getActivity(),passageActivity.class);
                intent.putExtra("pid",readId);
                intent.putExtra("title",List.get(position-1).getPtitle());
                intent.putExtra("time",List.get(position-1).getPtime());
                startActivity(intent);


            }
        });
    }



    private void initListView(JsonObject root, JsonPrimitive code){

        if(code.getAsString().equals("200")){

            JsonArray data = root.getAsJsonArray("data");
            progressDialog.dismiss();
            if(!data.toString().equals("[]")){

                if (page == 1){  //第一次进入
                    List = new Gson().fromJson(data,new TypeToken<List<passage>>(){}.getType());
                    newListAdatpter = new newListAdatpter();
                    news_lv.setAdapter(newListAdatpter);
                    //设置下拉刷新,加载更多监听
                    news_lv.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            page = 1;
                            initDate();
                            newListAdatpter.notifyDataSetChanged();

                        }

                        @Override
                        public void onLoadMore() {
                            page++;
                            initDate();
                        }
                    });
                }else {//加载更多
                    List<passage> pageList = new Gson().fromJson(data,new TypeToken<List<passage>>(){}.getType());
                    List.addAll(pageList);
                    newListAdatpter.notifyDataSetChanged();
                }


            }else {
                Toast.makeText(getActivity(),"没有更多了！",Toast.LENGTH_LONG).show();
            }

        }
    }


    /**
     * 改变点击过项目的颜色
     * @param view
     */
    private  void changeReadState(View view){
        TextView tv = (TextView) view.findViewById(R.id.news_title);
        tv.setTextColor(Color.DKGRAY);
    }



    public class newListAdatpter extends BaseAdapter{




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
                convertView = View.inflate(getContext(),R.layout.news_item_layout,null);
                viewHoder = new ViewHoder();
                viewHoder.imgeP = (ImageView) convertView.findViewById(R.id.news_p);
                viewHoder.tvTime = (TextView) convertView.findViewById(R.id.news_time);
                viewHoder.tvTitle = (TextView) convertView.findViewById(R.id.news_title);
                convertView.setTag(viewHoder);
            }else {
                viewHoder = (ViewHoder) convertView.getTag();
            }

            passage ps = (passage) getItem(position);
            bitmapUtils = new BitmapUtils(getActivity());
            viewHoder.tvTitle.setText(ps.getPtitle());
            viewHoder.tvTime.setText(ps.getPtime());
            bitmapUtils.configDefaultLoadingImage(R.drawable.bagg);
            bitmapUtils.display(viewHoder.imgeP,ConnectionUrl.ROOT_URL+"PassageImgs/"+ps.getPurl()+".jpg");
            String ids = PrefUtils.getString(getActivity(),"read_ids","");


            if (ids.contains(((passage) getItem(position)).getPid())){
                viewHoder.tvTitle.setTextColor(Color.DKGRAY);
            }else {
                viewHoder.tvTitle.setTextColor(Color.BLACK);
            }

            return convertView;
        }
    }

    static  class ViewHoder{
        public  TextView tvTitle;
        public TextView tvTime;
        public ImageView imgeP;
    }
}
