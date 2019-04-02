package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fsck.k9.R;

public class ContactList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Button button = (Button) findViewById(R.id.add_new_contact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddContactActivity();
            }
        });

    }

    public void openAddContactActivity() {
        Intent intent = new Intent(this, AddContact.class);
        startActivity(intent);
    }
}
