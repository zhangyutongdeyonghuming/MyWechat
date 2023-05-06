package com.example.mywechat.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywechat.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 判断登录了就跳转到主页，基于SharedPreferences实现用户登录信息的获取 存储
        SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = loginInfo.edit();
        // 获取内容
        String username = loginInfo.getString("username", "");
        String pwd = loginInfo.getString("password", "");
        TextView phoneText = findViewById(R.id.editTextPhone);
        TextView passwordText = findViewById(R.id.editTextTextPassword);
        // username不为空
        if (!username.equals("")) {
            phoneText.setText(username);
        }
        // pwd不为空
        if (!pwd.equals("")) {
            passwordText.setText(pwd);
        }
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener((v) -> {

            CharSequence phone = phoneText.getText();
            CharSequence password = passwordText.getText();
            if (phone == null || phone.toString().equals("")) {
                Toast.makeText(LoginActivity.this,"请输入账号！",Toast.LENGTH_SHORT).show();
                return;
            }
            if (password == null || password.toString().equals("")) {
                Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                return;
            }
            edit.putString("username", phone.toString());
            // 登陆成功，保存登录名密码
            edit.putString("password", password.toString());
            edit.apply();
            // 跳转main
            LoginActivity loginActivity = LoginActivity.this;
            Intent intent = new Intent(loginActivity, MainActivity.class);
            startActivity(intent);
            // 结束当前activity
            loginActivity.finish();
        });
    }
}