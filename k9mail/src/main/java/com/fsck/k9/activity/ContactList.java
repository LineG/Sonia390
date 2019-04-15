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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;
import com.fsck.k9.fragment.ContactListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    private ContactListAdapter contactAdapter;
    private String email;
    final ArrayList<Contact> contactList = new ArrayList<>();
    private EditText emailToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ListView listView;
        String emailFb;

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        emailFb = email.replace(".", "^");

        emailToDelete = (EditText) findViewById(R.id.email_to_delete);
        Button delete = (Button) findViewById(R.id.delete_contact);
        Button addContact = (Button) findViewById(R.id.add_new_contact);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = emailToDelete.getText().toString();
                String emailTemp = emailText.replace(".","^");

                if(emailText != null){
                    deleteContact(emailTemp);
                }
                else{
                    Toast.makeText(ContactList.this, "You did not choose an email to delete",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddContactActivity();
            }
        });

        listView = findViewById(R.id.contact_list_view);

        retrieveContact(emailFb);



        contactAdapter = new ContactListAdapter(this, contactList);

        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                sendContact(contact.getEmail());


            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void openAddContactActivity() {
        Intent intent = new Intent(this, AddContact.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }


    public void sendContact(String email) {

        Intent i = new Intent(this, MessageCompose.class);
        i.putExtra("contact_email", email);
        startActivity(i);


    }

    public void retrieveContact(final String email) {
        DatabaseReference contactsDb = FirebaseDatabase.getInstance().getReference().child(email).child("contacts");
        contactsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
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
                //comment
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //comment
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //comment
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //comment
            }
        });
    }

    public void deleteContact(final String email) {
        final DatabaseReference contactsDb = FirebaseDatabase.getInstance().getReference().child(email).child("contacts");
        contactsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    if(dataSnapshot.child(email).exists()){
                        String emailTemp = email.replace("^",".");
//                        contactsDb.child(email).removeValue();
                        for(int i=0; i<contactList.size(); i++){
                            if(contactList.get(i).getEmail().equals(emailTemp)){
                                contactList.remove(contactList.get(i));
                                contactAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                    else{
                        Toast.makeText(ContactList.this, "Email doesn't exist",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //comment
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //comment
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //comment
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //comment
            }
        });
    }
}
