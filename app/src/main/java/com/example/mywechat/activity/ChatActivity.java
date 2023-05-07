package com.example.mywechat.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.adapter.ChatAdapter;
import com.example.mywechat.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private final List<String> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String toUser = intent.getStringExtra("toUser");
        TextView toUserView = findViewById(R.id.textView3);
        String formatText = String.format("你正在与%s聊天", toUser);
        toUserView.setText(formatText);

        RecyclerView recyclerView = findViewById(R.id.message_list);
        ChatAdapter adapter = new ChatAdapter(ChatActivity.this, messageList);
        recyclerView.setAdapter(adapter);
        TextView editTextText = findViewById(R.id.editTextText);
        // 发送按钮
        Button sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener((v) -> {
            String msg = editTextText.getText().toString();
            if (msg.equals("")) {
                return;
            }
            // 存储消息到sqlite
            try (DBHelper dbHelper = new DBHelper(ChatActivity.this)) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 使用 ContentValues 来存储消息
                ContentValues values = new ContentValues();
                values.put("from_user", "me");
                values.put("to_user", toUser);
                values.put("msg", msg);
                values.put("time", System.currentTimeMillis());
                values.put("to_user_avatar", "https://p3-passport.byteimg.com/img/user-avatar/91cdea559783d73168410d491d1e89aa~180x180.awebp");

                // 插入数据
                db.insert("messages", null, values);

                // 关闭数据库连接
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            messageList.add(msg);
            adapter.notifyDataSetChanged();
            editTextText.setText("");

        });
    }
}
