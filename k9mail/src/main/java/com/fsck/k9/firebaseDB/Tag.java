package com.fsck.k9.firebasedb;

import android.graphics.Color;

public class Tag {

    String name;
    Color color;

    public Tag(String n, Color cl) {
        name = n;
        color = cl;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setColor(Color cl) {
        color = cl;
    }

    public Color getColor() {
        return color;
    }


}
