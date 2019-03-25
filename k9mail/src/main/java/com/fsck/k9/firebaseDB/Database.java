package com.fsck.k9.firebasedb;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Database {

    private FirebaseDatabase database;

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    public void loadMessages() {
        //load messages from mailbox here
    }

    public void saveTags(String tag, int index) {
        //save customize tags here, strings and colors need to added as well
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        switch (index) {
            case 1:
                myRef = database.getReference("tag1");
                myRef.setValue(tag);
                break;
            case 2:
                myRef = database.getReference("tag2");
                myRef.setValue(tag);
                break;
            case 3:
                myRef = database.getReference("tag3");
                myRef.setValue(tag);
                break;
        }
    }
}
