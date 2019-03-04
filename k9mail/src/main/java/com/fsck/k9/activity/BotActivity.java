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
    protected EditText userInput;
    protected RecyclerView recyclerView;
    protected BotMessageAdapter messageAdapter;
    protected List<ResponseMessage> responseMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot);
        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessageList = new ArrayList<>();
        messageAdapter = new BotMessageAdapter(responseMessageList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

        ResponseMessage initialBotMessage = new ResponseMessage(getString(R.string.bot_initial_message), false);
        responseMessageList.add(initialBotMessage);
        messageAdapter.notifyDataSetChanged();

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    ResponseMessage responseMessage = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(responseMessage);

                    ResponseMessage responseMessage2 = new ResponseMessage(commandChoice(userInput.getText().toString()), false);
                    responseMessageList.add(responseMessage2);
                    messageAdapter.notifyDataSetChanged();
                    userInput.setText("");
                    if (!isLastVisible()){
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1)};
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

    String commandChoice(String choice) {
        if(choice.equals("!cm mc")) {
            Intent intent = new Intent(this, MessageCompose.class);
            startActivity(intent);
            finish();
            return choice;
        }
        else if(choice.equals("!cm ml")) {
            Intent intent = new Intent(this, Accounts.class);
            startActivity(intent);
            finish();
            return choice;
        }
        else if(choice.equals("!cm faq")) {  // redirects to page
            choice = getString(R.string.frequently_asked_questions);
            return choice;
        }
        else if(choice.equalsIgnoreCase("Hello") || choice.equalsIgnoreCase("Hi")) {
            choice = getString(R.string.bot_hello_message);
            return choice;
        }
        else if(choice.equalsIgnoreCase("account overview")) {  //the bot explains
            choice = getString(R.string.account_overview);
            return choice;
        }
        else if(choice.equalsIgnoreCase("configure folders")) {  //the bot explains
            choice = getString(R.string.configure_folders);
            return choice;
        }
        else if(choice.equalsIgnoreCase("additional mails")) {  //the bot explains
            choice = getString(R.string.additional_mail);
            return choice;
        }
        else if(choice.equalsIgnoreCase("save settings")) {  //the bot explains
            choice = getString(R.string.save_settings);
            return choice;
        }
        else if(choice.equalsIgnoreCase("signature")) { //the bot explains
            choice = getString(R.string.signature);
            return choice;
        }
        else if(choice.equalsIgnoreCase("about")) { //the bot explains
            choice = getString(R.string.about);
            return choice;
        }
        else {
            return choice+" is not a command";
        }

    }

}

