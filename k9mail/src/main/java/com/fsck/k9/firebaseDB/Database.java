package com.fsck.k9.firebasedb;
import com.google.firebase.database.*;

public class Database {

    private FirebaseDatabase database;

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    public void loadMessages() {
        //load messages from mailbox here
    }

    public void saveTags(String tag) {
        //save customize tags here
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
    }
}
