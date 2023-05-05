package com.example.mywechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mywechat.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 判断登录了就跳转到主页，基于SharedPreferences实现用户登录信息的获取 存储
        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        // 获取内容
        String username = loginInfo.getString("username", "");
        // 不为空则为已登录，跳转到main
        if (!username.equals("")) {
            LoginActivity loginActivity = LoginActivity.this;
            Intent intent = new Intent(loginActivity, MainActivity.class);
            startActivity(intent);
            // 结束当前activity
            loginActivity.finish();
        }
    }
}