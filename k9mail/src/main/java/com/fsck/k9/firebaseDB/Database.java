package com.fsck.k9.firebasedb;
//import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Database {

    private FirebaseDatabase database;

    public Database() {
        //database = FirebaseDatabase.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tag1");
        Tag tag2 = new Tag("line", "hello");
        Tag tag3 = new Tag("line", "hello");
        Map map = new HashMap<>();
        map.put("key",tag2);
        myRef.setValue(map);
    }

    public void loadMessages() {
        //load messages from mailbox here
    }

    public void saveTags(String tag, int index) {

        //save customize tags here, strings and colors need to added as well
        //we might need to make an object tag, to hold string and color values
        //this should be moved somewhere else maybe
//
//        DatabaseReference myRef;
//        switch (index) {
//            case 1:
//                myRef = database.getReference("tag1");
//                myRef.setValue(tag);
//                break;
//            case 2:
//                myRef = database.getReference("tag2");
//                myRef.setValue(tag);
//                break;
//            case 3:
//                myRef = database.getReference("tag3");
//                myRef.setValue(tag);
//                break;
//        }

    }

}
