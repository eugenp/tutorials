package com.baeldung.cglib.proxy;

import com.baeldung.cglib.mixin.*;
import net.sf.cglib.proxy.Mixin;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class MixinTest {

    @Test
    public void testMixin() throws Exception {
        //when
        Mixin mixin = Mixin.create(
                new Class[]{Interface1.class, Interface2.class, MixinInterface.class},
                new Object[]{new Class1(), new Class2()}
        );
        MixinInterface mixinDelegate = (MixinInterface) mixin;

        //then
        assertEquals("first", mixinDelegate.first());
        assertEquals("second", mixinDelegate.second());
    }
}
