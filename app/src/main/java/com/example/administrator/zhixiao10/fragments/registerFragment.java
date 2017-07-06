package com.example.administrator.zhixiao10.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.Activity.WelcomeActivity;
import com.example.administrator.zhixiao10.bean.User;
import com.example.administrator.zhixiao10.lib.ConnManager;
import com.example.administrator.zhixiao10.lib.callback.ObjCallback;
import com.lidroid.xutils.http.RequestParams;


public class registerFragment extends Fragment implements View.OnClickListener {

    private EditText reg_Id;
    private EditText reg_pass;
    private EditText reg_repass;
    private Button reg;
    private Dialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.register, container, false);
        reg = (Button) root.findViewById(R.id.reg);
        reg_Id = (EditText) root.findViewById(R.id.reg_Id);
        reg_pass = (EditText) root.findViewById(R.id.reg_pass);
        reg_repass = (EditText) root.findViewById(R.id.reg_repass);

        reg.setOnClickListener(this);

        return root;
    }



    public boolean TestFormat(){
        if(reg_Id.getText().toString().equals("") ||reg_Id.getText().toString() == null){
            Toast.makeText(getActivity(),"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(reg_pass.getText().toString().equals("") || reg_pass.getText().toString() ==null){
            Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(reg_repass.getText().toString().equals("") ||reg_repass.getText().toString() == null){
            Toast.makeText(getActivity(),"请再次输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(reg_repass.getText().toString().equals(reg_repass.getText().toString())){
            return true;
        }else {
            Toast.makeText(getActivity(),"两次密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg:
                if(TestFormat()){
                    progressDialog = new Dialog(getActivity(),R.style.progress_dialog);
                    progressDialog.setContentView(R.layout.dialog);
                    progressDialog.setCancelable(true);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
                    msg.setText("请稍等..");
                    progressDialog.show();

                    RequestParams requestParams = new RequestParams();
                    requestParams.addBodyParameter("id",reg_Id.getText().toString());
                    requestParams.addBodyParameter("password",reg_pass.getText().toString());
                    ConnManager.getInstance(getActivity()).sendRequest(ConnectionUrl.SERVICE_URL+"Register", requestParams, new ObjCallback<User>() {

                        @Override
                        public void onSuccess(User data) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),data.getID()+"注册成功",Toast.LENGTH_SHORT).show();
                            WelcomeActivity  welcomeActivity = (WelcomeActivity) getActivity();
                            welcomeActivity.changPageInLogin();

                        }

                        @Override
                        public void onFailure() {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"账号已被注册！",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

        }
    }
}
