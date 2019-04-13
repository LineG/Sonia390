package com.fsck.k9.firebasedb;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    private Contact c;

    @Before
    public void setUp() throws Exception {

        c = new Contact("line","ghanem","ghanemline");

    }

    @Test
    public void getName() {

        assertEquals(c.getName(), "line");
    }

    @Test
    public void setName() {

        c.setName("lara");
        assertEquals("lara", c.getName());
    }

    @Test
    public void getLastName() {

        assertEquals(c.getLastName(),"ghanem");
    }

    @Test
    public void setLastName() {

        c.setLastName("chan");
        assertEquals("chan", c.getLastName());
    }


    @Test
    public void setEmail() {

        c.setEmail("linelineline");
        assertEquals("linelineline", c.getEmail());
    }
}