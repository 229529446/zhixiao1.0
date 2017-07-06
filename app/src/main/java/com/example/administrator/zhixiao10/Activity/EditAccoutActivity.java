package com.example.administrator.zhixiao10.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhixiao10.R;
import com.example.administrator.zhixiao10.Url.ConnectionUrl;
import com.example.administrator.zhixiao10.bean.User;
import com.example.administrator.zhixiao10.lib.ConnManager;
import com.example.administrator.zhixiao10.lib.callback.ObjCallback;
import com.example.administrator.zhixiao10.utils.PrefUtils;
import com.lidroid.xutils.http.RequestParams;

public class EditAccoutActivity extends Activity {


    private EditText address, phone, username;
    private RadioGroup sex;
    private LinearLayout send;
    private Dialog progressDialog;
    private String account,pwd;
    private ImageButton back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_edit_accout);

        initView();
        initDate();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SexString = null;
                String AddressString;
                String PhoneNumString;
                String NameString;

                if (format()) {
                    for (int i = 0; i < sex.getChildCount(); i++) {
                        RadioButton r = (RadioButton) sex.getChildAt(i);
                        if (r.isChecked()) {
                            SexString = r.getText().toString();
                            break;
                        }
                    }

                    AddressString = address.getText().toString();
                    PhoneNumString = phone.getText().toString();
                    NameString = username.getText().toString();

                    sendEdit(SexString,AddressString ,PhoneNumString,NameString);
                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }


    private void sendEdit(String sexString, String addressString, String phoneNumString, String nameString) {

        Log.i("sendEdit", "sendEdit: "+sexString+"++++"+addressString+"++++"+phoneNumString+"++++"+nameString+"++++"+account);

        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("请稍等..");
        progressDialog.show();


        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("id",account);
        requestParams.addBodyParameter("sex",sexString.equals("男")?2+"":1+"");
        requestParams.addBodyParameter("phone",phoneNumString);
        requestParams.addBodyParameter("username",nameString);
        requestParams.addBodyParameter("address",addressString);
        requestParams.addBodyParameter("password",pwd);

        ConnManager.getInstance(this).sendRequest(ConnectionUrl.SERVICE_URL + "Update", requestParams, new ObjCallback<User>() {
            @Override
            public void onSuccess(User data) {
                progressDialog.dismiss();
                Toast.makeText(EditAccoutActivity.this,data.getID()+"修改成功",Toast.LENGTH_SHORT).show();


                //账号信息持久化
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountName",data.getUserName());
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountID",data.getID());
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountPwd",data.getPassword());
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountPhoneNum",data.getPhoneNum());
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountSex",data.getSex()+"");
                PrefUtils.setAccount(EditAccoutActivity.this,"AccountAddress",data.getAddress());
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(EditAccoutActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
            }
        });









    }

    private boolean format() {
        boolean b = true;
//        Log.i("sendEd", "format: "+address.getText());
//        Log.i("sendEd", "format: "+phone.getText());

        if (address.getText().toString().equals(" ") ||address.getText().toString().equals("") || address.getText().toString() == null) {
            Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.getText().toString().equals(" ") ||phone.getText().toString().equals("") || address.getText().toString() == null) {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }else if (username.getText().toString().equals(" ") ||username.getText().toString().equals("") || username.getText().toString() == null) {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return b;

    }

    private void initView() {

        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        username = (EditText) findViewById(R.id.username);
        sex = (RadioGroup) findViewById(R.id.sex);
        send = (LinearLayout) findViewById(R.id.send);
        back = (ImageButton) findViewById(R.id.back_btn);



    }


    private void initDate() {
        progressDialog = new Dialog(this, R.style.progress_dialog);
        account = PrefUtils.getAccount(this,"AccountID","");
        pwd = PrefUtils.getAccount(this,"AccountPwd","");

        if (PrefUtils.getAccount(this,"AccountSex","").equals("0")){
            sex.getChildAt(0).setClickable(true);
        }else {
            if (PrefUtils.getAccount(this,"AccountSex","还没填写噢").equals("1")){
                sex.getChildAt(0).setClickable(true);
            }else {
                sex.getChildAt(1).setClickable(true);
            }
        }

        address.setHint(PrefUtils.getAccount(this,"AccountAddress","").equals("")?"例如:广东广州":PrefUtils.getAccount(this,"AccountAddress",""));
        phone.setHint(PrefUtils.getAccount(this,"AccountPhoneNum","").equals("")?"例如:13800138000":PrefUtils.getAccount(this,"AccountPhoneNum",""));
        username.setHint(PrefUtils.getAccount(this,"AccountName","").equals("")?"游游乐用户":PrefUtils.getAccount(this,"AccountName",""));

    }


}
