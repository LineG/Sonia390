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
                    if (!isLastVisible()) {
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1); }
                }
                return false;
            }
        });
    }
    protected boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }

    protected String commandChoice(String choice) {
        String selection = choice;
        if ("!cm mc".equals(choice)) {
            Intent intent = new Intent(this, MessageCompose.class);
            startActivity(intent);
            finish();
            return selection;
        }
        else if ("!cm ml".equals(choice)) {
            Intent intent = new Intent(this, Accounts.class);
            startActivity(intent);
            finish();
            return selection;
        }
        else if ("!cm faq".equals(choice)) {  // redirects to page
            selection = getString(R.string.frequently_asked_questions);
            return selection;
        }
        else if (choice.equalsIgnoreCase("Hello") || choice.equalsIgnoreCase("Hi")) {
            selection = getString(R.string.bot_hello_message);
            return selection;
        }
        else if (choice.equalsIgnoreCase("account overview")) {  //the bot explains
            selection = getString(R.string.account_overview);
            return selection;
        }
        else if (choice.equalsIgnoreCase("configure folders")) {  //the bot explains
            selection = getString(R.string.configure_folders);
            return selection;
        }
        else if (choice.equalsIgnoreCase("additional mails")) {  //the bot explains
            selection = getString(R.string.additional_mail);
            return selection;
        }
        else if (choice.equalsIgnoreCase("save settings")) {  //the bot explains
            selection = getString(R.string.save_settings);
            return selection;
        }
        else if (choice.equalsIgnoreCase("signature")) { //the bot explains
            selection = getString(R.string.signature);
            return selection;
        }
        else if (choice.equalsIgnoreCase("about")) { //the bot explains
            selection = getString(R.string.about);
            return selection;
        }
        else {
            return selection + " is not a command";
        }

    }

}

