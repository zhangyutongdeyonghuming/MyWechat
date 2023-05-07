package com.example.mywechat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywechat.R;
import com.example.mywechat.adapter.MessageAdapter;
import com.example.mywechat.db.DBHelper;
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
        // 创建数据库，添加数据
        try (DBHelper dbHelper = new DBHelper(MsgListActivity.this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("from_user", "me");
            values.put("to_user", "小张");
            values.put("msg", "hello");
            values.put("time", new Date().getTime());
            values.put("to_user_avatar", "https://p3-passport.byteimg.com/img/user-avatar/91cdea559783d73168410d491d1e89aa~180x180.awebp");
            db.insert("messages", null, values);

        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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
            TextView tvItem = view.findViewById(R.id.to_user);
            Toast.makeText(MsgListActivity.this, "You clicked item " + tvItem.getText().toString(), Toast.LENGTH_SHORT).show();
            // 点击跳转发送消息页面
        });
    }
}