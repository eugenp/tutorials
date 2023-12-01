package com.baeldung.stringbuffer;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HashCodeTest {

    String str = "Spring";
    StringBuffer sBuf = new StringBuffer("Spring");

    @Test
    public void whenStringConcat_thenHashCodeChanges() {
        HashCode hc = new HashCode();

        long initialStringHashCode = hc.getHashCodeString(str);
        long initialSBufHashCode =  hc.getHashCodeSBuffer(sBuf);

        str += "Framework";
        sBuf.append("Framework");

        assertThat(initialStringHashCode).isNotEqualTo(hc.getHashCodeString(str));
        assertThat(initialSBufHashCode).isEqualTo(hc.getHashCodeSBuffer(sBuf));
    }
}
