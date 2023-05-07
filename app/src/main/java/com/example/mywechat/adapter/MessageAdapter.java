package com.example.mywechat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.R;
import com.example.mywechat.entity.Message;
import com.example.mywechat.util.DownloadImageTask;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private final List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, parent, false);
        }

        // 绑定数据到视图上
        Message message = messageList.get(position);
        ImageView toUserAvatar = convertView.findViewById(R.id.to_user_avatar);
        TextView toUser = convertView.findViewById(R.id.to_user);
        TextView msg = convertView.findViewById(R.id.msg);
        TextView time = convertView.findViewById(R.id.time);

        new DownloadImageTask(toUserAvatar).execute(message.getToUserAvatar());
        toUser.setText(message.getToUser());
        msg.setText(message.getMsg());
        time.setText(message.getTime());

        return convertView;
    }
}
