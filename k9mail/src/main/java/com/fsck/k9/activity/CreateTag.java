package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fsck.k9.R;
import com.fsck.k9.fragment.MessageListFragment;

public class CreateTag extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tag);

//
//        Intent receiveIntent = this.getIntent();
////        Bundle bundle = getIntent().getExtras();
//        String id = receiveIntent.getExtras("messageId");
//
//
////        String id = bundle.getString("messageId");
//        Log.d("Mytag",id);
    }
}
