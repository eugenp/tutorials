package com.baeldung.takes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.takes.rq.RqFake;
import org.takes.rs.RsPrint;

public class TakesContactUnitTest {

    @Test
    public void givenTake_whenInvokeActMethod_thenRespond() throws Exception {
        String resp = new RsPrint(new TakesContact().act(new RqFake())).printBody();
        assertEquals("Contact us at https://www.baeldung.com", resp);
    }

}
