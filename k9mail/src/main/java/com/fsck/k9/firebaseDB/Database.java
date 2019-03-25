package com.fsck.k9.firebaseDB;
import com.google.firebase.database.*;

public class Database {

    private FirebaseDatabase database;

    public Database() {

    }

    public void loadMessages() {

    }

    public void saveTags(String tag) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
    }
}
