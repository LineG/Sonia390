package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;
import com.fsck.k9.fragment.ContactListAdapter;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    private ListView listView;
    private ContactListAdapter contactAdapter;

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

        listView = findViewById(R.id.contact_list_view);

        final ArrayList<Contact> contactList = new ArrayList<>();

        contactList.add(new Contact("line", "ghanem",
                "ghanemline@gmail.com"));
        contactList.add(new Contact("lara", "ghanem",
                "laraghanem@gmail.com"));

        contactAdapter = new ContactListAdapter(this, contactList);

        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Contact contact = contactList.get(position);
                sendContact(contact.getEmail());


            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        return;
    }

    public void openAddContactActivity() {
        Intent intent = new Intent(this, AddContact.class);
        startActivity(intent);
    }


    private void sendContact(String email) {

        Intent i = new Intent(this, MessageCompose.class);
        i.putExtra("contact_email", email);
        startActivity(i);


    }
}
