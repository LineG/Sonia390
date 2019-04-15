package com.fsck.k9.activity;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsck.k9.R;
import com.fsck.k9.firebasedb.Contact;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddContact extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText contactEmail;
    private TextView invalidEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button saveContacts;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        saveContacts = (Button) findViewById(R.id.save_contact);
        firstName = (EditText) findViewById(R.id.add_contact_name);
        lastName = (EditText) findViewById(R.id.add_contact_lastName);
        contactEmail = (EditText) findViewById(R.id.add_contact_email);
        invalidEmail = (TextView) findViewById(R.id.invalid_email);

        final Intent intent = getIntent();
        final String emailTemp = intent.getStringExtra("email");
        String email = emailTemp.replace(".", "^");


        final DatabaseReference contactsDb = FirebaseDatabase.getInstance().getReference().child(email).child("contacts");


        saveContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameText = firstName.getText().toString();
                String lastNameText = lastName.getText().toString();
                String contactEmailText = contactEmail.getText().toString();

                if ((firstNameText.equals("")) && !checkIfemailIsValid(contactEmailText)) {
                    invalidEmail.setText("Please Enter A First Name and A Valid Email");
                    invalidEmail.setVisibility(View.VISIBLE);
                }
                else if (!checkIfemailIsValid(contactEmailText)){
                    invalidEmail.setVisibility(View.VISIBLE);
                }
                else if (firstNameText.equals("")){
                    invalidEmail.setText("Please Enter A First Name");
                    invalidEmail.setVisibility(View.VISIBLE);
                }
                else {

                    String emailForSaving = contactEmailText.replace(".", "^");

                    Contact contact = new Contact(firstNameText, lastNameText, contactEmailText);
                    Map contactInfo = new HashMap<>();

                    contactInfo.put(emailForSaving, contact);

                    contactsDb.updateChildren(contactInfo);

                    String email = emailTemp;
                    Intent intent = new Intent(AddContact.this, ContactList.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public boolean checkIfemailIsValid(String email) {

        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
