package com.fsck.k9.firebasedb;

import android.graphics.Color;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TagContainer {

    ArrayList<Tag> tagArrayList;

    public TagContainer() {
        tagArrayList = new ArrayList<Tag>(3);
    }

    public ArrayList<Tag> getTagContainer() {
        return tagArrayList;
    }

    public void saveTag(String tag, int index, Color cl) {

        //save customize tags here, strings and colors need to added as well
        //we might need to make an object tag, to hold string and color values
        Tag tagTemp = new Tag(tag, cl);
        switch (index) {
            case 1:
                tagArrayList.add(tagTemp);
                break;
            case 2:
                tagArrayList.add(tagTemp);
                break;
            case 3:
                tagArrayList.add(tagTemp);
                break;
        }
    }

    public void editTagName(String name, int index) {

    }

    public void editTagColor(Color cl, int index) {

    }
}
