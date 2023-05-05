package com.example.mywechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mywechat.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // 新建子线程，延迟2秒后跳转页面LoginActivity
        new Handler().postDelayed(() -> {
            // 页面跳转到微信登录注册页面
            LoadingActivity loadingActivity = LoadingActivity.this;
            Intent intent = new Intent(loadingActivity, LoginActivity.class);
            startActivity(intent);
            // 结束当前activity
            loadingActivity.finish();
        }, 2000);
    }
}