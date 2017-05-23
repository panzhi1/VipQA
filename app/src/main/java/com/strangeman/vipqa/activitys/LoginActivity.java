package com.strangeman.vipqa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.CheckLoginState;
import com.strangeman.vipqa.entity.User;
import com.strangeman.vipqa.network.PostInfo;
import com.strangeman.vipqa.utils.VolleyCallback;


/**
 * Created by pilot on 2017/5/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button loginButton;
    private CheckLoginState checkLoginState;
    private String userId;
    private String passwd;
    private EditText inputAccount;
    private EditText inputPassword;
    private PostInfo postInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        postInfo=new PostInfo(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginButton = (Button) findViewById(R.id.userLogin);
        loginButton.setOnClickListener(this);
        inputAccount=(EditText)findViewById(R.id.inputAccount);
        inputPassword=(EditText)findViewById(R.id.inputPassword);


    }

    @Override
    public void onClick(View v) {
        checkIfSuccess();

    }

    public void checkIfSuccess(){
        userId=inputAccount.getText().toString();
        passwd=inputPassword.getText().toString();
        if(userId!=null&&(userId.length()>6)&&(userId.length()<10)&&passwd!=null&&
                (passwd.length()>6)&&(passwd.length()<10)) {
            postInfo.LoginIn(new VolleyCallback<User>() {
                @Override
                public void onSuccess(User result) {
                    if (result == null) {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                    } else {
                        checkLoginState.setUser(result);
                        finish();
                    }
                }
            }, userId, passwd);
        }else Toast.makeText(this,"请输入相应位数的账号和密码",Toast.LENGTH_LONG).show();
    }
}
