package com.example.mywechat.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.R;
import com.example.mywechat.adapter.MessageAdapter;
import com.example.mywechat.entity.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MsgListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_list);
        Button exitBtn = findViewById(R.id.btn_exit);
        Log.d("MyApp", "msg activity");
        exitBtn.setOnClickListener((v) -> {
            Log.d("MyApp", "btn_exit clicked");
            // 创建弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(MsgListActivity.this);
            builder.setTitle("确认退出登录？");
            builder.setPositiveButton("确认", (dialog, which) -> {
                // 退出登录
                SharedPreferences loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
                SharedPreferences.Editor edit = loginInfo.edit();
                edit.putString("login", "0");
                edit.apply();
                // 跳转页面
                Intent intent = new Intent(MsgListActivity.this, LoadingActivity.class);
                startActivity(intent);
                // 结束当前activity
                MsgListActivity.this.finish();
            });
            // 显示弹窗
            builder.show();
        });
        Button weatherBtn = findViewById(R.id.btn_weather);
        weatherBtn.setOnClickListener((v) -> {
            // 跳转天气页面
            Intent intent = new Intent(MsgListActivity.this, WeatherActivity.class);
            startActivity(intent);
        });
        refreshMessageList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshMessageList();
    }

    private void refreshMessageList() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 查询回显
        SQLiteDatabase db = this.openOrCreateDatabase("msg.db", Context.MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM messages WHERE id IN (SELECT MAX(id) FROM messages GROUP BY to_user) ORDER BY time DESC", null);
        // 添加到页面展示
        List<Message> messages = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String fromUser = cursor.getString(cursor.getColumnIndex("from_user"));
                @SuppressLint("Range") String toUser = cursor.getString(cursor.getColumnIndex("to_user"));
                @SuppressLint("Range") String msg = cursor.getString(cursor.getColumnIndex("msg"));
                @SuppressLint("Range") long time = cursor.getLong(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String toUserAvatar = cursor.getString(cursor.getColumnIndex("to_user_avatar"));
                String dateStr = sdf.format(new Date(time));
                Message message = new Message(id, fromUser, toUser, msg, dateStr, toUserAvatar);
                Log.v("msg", message.toString());
                messages.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        MessageAdapter messageAdapter = new MessageAdapter(messages);
        ListView listView = findViewById(R.id.msg_list);
        listView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView toUserTextView = view.findViewById(R.id.to_user);
            String toUser = toUserTextView.getText().toString();
            // 点击跳转发送消息页面
            MsgListActivity msgListActivity = MsgListActivity.this;
            Intent intent = new Intent(msgListActivity, ChatActivity.class);
            intent.putExtra("toUser", toUser);
            startActivity(intent);
        });
    }
}