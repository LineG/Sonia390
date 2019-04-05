package com.fsck.k9.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;
import com.fsck.k9.fragment.ContactListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    private ListView listView;
    private ContactListAdapter contactAdapter;
    private String email;
    private String emailFb;
    final ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        emailFb = email.replace(".", "^");

        Button button = (Button) findViewById(R.id.add_new_contact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddContactActivity();
            }
        });

        listView = findViewById(R.id.contact_list_view);

        retrieveTags(emailFb);

//        final ArrayList<Contact> contactList = new ArrayList<>();
//
//        contactList.add(new Contact("line", "ghanem",
//                "ghanemline@gmail.com"));
//        contactList.add(new Contact("lara", "ghanem",
//                "laraghanem@gmail.com"));



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
        intent.putExtra("email", email);
        startActivity(intent);
    }


    private void sendContact(String email) {

        Intent i = new Intent(this, MessageCompose.class);
        i.putExtra("contact_email", email);
        startActivity(i);


    }

    public void retrieveTags(final String email) {
        DatabaseReference contactsDb = FirebaseDatabase.getInstance().getReference().child(email).child("contacts");
        contactsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    String firstName = dataSnapshot.child("name").getValue().toString();
                    String lastName = dataSnapshot.child("lastName").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    Contact contact = new Contact(firstName, lastName, email);
                    Log.d("contact", contact.toString());
                    contactList.add(contact);
                    contactAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
