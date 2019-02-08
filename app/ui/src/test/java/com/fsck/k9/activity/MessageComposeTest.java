package com.fsck.k9.activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageComposeTest {

    MessageCompose ms = new MessageCompose();
    int testCounter;

    @Before
    public void setUp() throws Exception {

        testCounter = ms.templateArr[0];

        assertEquals(0, testCounter);

    }

    @After
    public void tearDown() throws Exception {

        testCounter = ms.templateArr[0];

        assertEquals(1, testCounter);

    }

    @Test
    public void templateCounter() {

        ms.templateFunc(ms.templateArr, 0);

    }
}