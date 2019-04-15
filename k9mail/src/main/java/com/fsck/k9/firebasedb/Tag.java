package com.fsck.k9.firebasedb;

public class Tag {

    private String name;
    //hexadecimal for color
    private int color;

    public Tag() {
        
    }

    public Tag(String n, int cl) {
        name = n;
        color = cl;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setColor(int cl) {
        color = cl;
    }

    public int getColor() {
        return color;
    }
}
