package com.example.administrator.zhixiao10.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.Activity.MainActivity;
import com.example.administrator.zhixiao10.Activity.zhixiaoApp;
import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.ChatBean.Message;
import com.example.administrator.zhixiao10.bean.ChatBean.MessageType;
import com.example.administrator.zhixiao10.bean.User;
import com.example.administrator.zhixiao10.lib.ChatConnection;
import com.example.administrator.zhixiao10.lib.ConnManager;
import com.example.administrator.zhixiao10.lib.callback.ObjCallback;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.example.administrator.zhixiao10.utils.ThreadUtils;
import com.lidroid.xutils.http.RequestParams;

import java.io.IOException;


public class loginFragment extends Fragment implements View.OnClickListener {

    private Button Log;
    private EditText LogId;
    private EditText LogPassword;
    private Dialog progressDialog;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.login, container, false);

        Log = (Button) root.findViewById(R.id.Log);
        LogId = (EditText) root.findViewById(R.id.Log_id);
        LogPassword = (EditText) root.findViewById(R.id.Log_pass);


        Log.setOnClickListener(this);



        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Log:
                if (TestFormat()){

                    progressDialog = new Dialog(getActivity(),R.style.progress_dialog);
                    progressDialog.setContentView(R.layout.dialog);
                    progressDialog.setCancelable(true);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
                    msg.setText("登录中");
                    progressDialog.show();
                    RequestParams requestParams = new RequestParams();
                    requestParams.addBodyParameter("id",LogId.getText().toString());
                    requestParams.addBodyParameter("password",LogPassword.getText().toString());
                    ConnManager.getInstance(getActivity()).sendRequest(ConnectionUrl.SERVICE_URL+"Login", requestParams, new ObjCallback<User>() {
                        @Override
                        public void onSuccess(User data) {
                            Toast.makeText(getActivity(),"欢迎你"+data.getID(),Toast.LENGTH_SHORT).show();

                            //账号信息持久化
                            PrefUtils.setAccount(getActivity(),"AccountName",data.getUserName());
                            PrefUtils.setAccount(getActivity(),"AccountID",data.getID());
                            PrefUtils.setAccount(getActivity(),"AccountPwd",data.getPassword());
                            PrefUtils.setAccount(getActivity(),"AccountPhoneNum",data.getPhoneNum());
                            PrefUtils.setAccount(getActivity(),"AccountSex",data.getSex()+"");
                            PrefUtils.setAccount(getActivity(),"AccountAddress",data.getAddress());
//                            android.util.Log.i("LOGIN_DATA", "onSuccess: "+data.toString());

                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        }
                        @Override
                        public void onFailure() {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"账号或者密码错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

        }
    }






    public boolean TestFormat(){

        if(LogId.getText().toString().equals("") ||LogId.getText().toString() == null){
            Toast.makeText(getActivity(),"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(LogPassword.getText().toString().equals("") || LogPassword.getText().toString() ==null){
            Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }

}
