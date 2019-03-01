package com.fsck.k9.activity;

import android.content.Intent;
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


import com.fsck.k9.ResponseMessage;
import com.fsck.k9.BotMessageAdapter;

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

        ResponseMessage initialBotMessage = new ResponseMessage("Here is a list of built in commands \n" +
                " '!cm mc': redirects to message compose \n"+
                " '!cm ml': redirects to message list \n"+
                " '!cm faq2': BOT gives us faqs \n"+
                " '!cm faq': Frequently asked questions\n\n" +
                "Enter a command or just say hi :D", false);
        responseMessageList.add(initialBotMessage);
        messageAdapter.notifyDataSetChanged();

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(responseMessage);
//                    ResponseMessage responseMessage2 = new ResponseMessage(userInput.getText().toString(), false);
                    ResponseMessage responseMessage2 = new ResponseMessage(commandChoice(userInput.getText().toString()), false);
                    responseMessageList.add(responseMessage2);
                    messageAdapter.notifyDataSetChanged();
                    userInput.setText("");
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

    String commandChoice(String choice){
        if(choice.equals("!cm mc")){
            Intent intent = new Intent(this, MessageCompose.class);
            startActivity(intent);
            finish();
            return choice;
        }
        else if(choice.equals("!cm ml")){
            Intent intent = new Intent(this, Accounts.class);
            startActivity(intent);
            finish();
            return choice;
        }
        else if(choice.equals("!cm faq")){ // redirects to page
            Intent intent = new Intent(this, FrequentlyAskedQuestions.class);
            startActivity(intent);
            finish();
            return choice;
        }
        else if(choice.equals("!cm faq2")){ //the bot explains
            return "write faq in order and with numbers so that the user chooses a question by choosing a number";
        }
        else if(choice.equalsIgnoreCase("Hello") || choice.equalsIgnoreCase("Hi")){
            choice = "Hello I'm K9BOT :) \nPlease enter one of my built-in commands\n"+
                    "Here is a list of built in commands \n" +
                    " '!cm mc': redirects to message compose \n"+
                    " '!cm ml': redirects to message list \n"+
                    " '!cm faq': Frequently asked questions";
            return choice;
        }
        else{
            return choice+" is not a command";
        }
    }
}