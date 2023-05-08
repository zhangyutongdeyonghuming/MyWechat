package com.example.mywechat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.mywechat.activity.MsgListActivity;
import com.example.mywechat.db.DBHelper;

import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        try (DBHelper dbHelper = new DBHelper(appContext)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("from_user", "me");
            values.put("to_user", "小张");
            values.put("msg", "hello");
            values.put("time", new Date().getTime());
            values.put("to_user_avatar", "https://p3-passport.byteimg.com/img/user-avatar/91cdea559783d73168410d491d1e89aa~180x180.awebp");
            db.insert("messages", null, values);

        }
    }
}