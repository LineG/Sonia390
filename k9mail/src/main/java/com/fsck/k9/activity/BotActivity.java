package com.fsck.k9.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.fsck.k9.R;


import com.fsck.k9.BotMessageAdapter;
import com.fsck.k9.ResponseMessage;

public class BotActivity extends AppCompatActivity {
    EditText userInput;
    RecyclerView recyclerView;
    BotMessageAdapter messageAdapter;
    List<ResponseMessage> responseMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot);
        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new BotMessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(responseMessage);
                    ResponseMessage responseMessage2 = new ResponseMessage(userInput.getText().toString(), false);
                    responseMessageList.add(responseMessage2);
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                }
                return false;
            }
        });
    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bot);
//    }
//    EditText userInput;
//    RecyclerView recyclerView;
//    BotMessageAdapter messageAdapter;
//    List<ResponseMessage> responseMessageList;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.empty_message_view);
//        userInput = findViewById(R.id.empty_trash);
//        recyclerView = findViewById(R.id.empty_trash);
//        responseMessageList = new ArrayList<>();
//        messageAdapter = new BotMessageAdapter(responseMessageList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//        recyclerView.setAdapter(messageAdapter);
//
//        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_SEND) {
//                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
//                    responseMessageList.add(responseMessage);
//                    ResponseMessage responseMessage2 = new ResponseMessage(userInput.getText().toString(), false);
//                    responseMessageList.add(responseMessage2);
//                    messageAdapter.notifyDataSetChanged();
//                    if (!isLastVisible())
//                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
//                }
//                return false;
//            }
//        });
//    }
//    boolean isLastVisible() {
//        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
//        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
//        int numItems = recyclerView.getAdapter().getItemCount();
//        return (pos >= numItems);
//    }
}