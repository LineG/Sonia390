package com.fsck.k9.firebasedb;

public class Tag {

    private String name;
    //hexadecimal for color
    private String color;

    public Tag(){
        
    }

    public Tag(String n, String cl) {
        name = n;
        color = cl;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setColor(String cl) {
        color = cl;
    }

    public String getColor() {
        return color;
    }
}
