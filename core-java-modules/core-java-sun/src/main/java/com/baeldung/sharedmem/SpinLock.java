package com.baeldung.sharedmem;

//import sun.misc.Unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class SpinLock {
    private static final Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        }
        catch(NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private final long addr;

    public SpinLock(long addr) {
        this.addr = addr;
    }

    public boolean tryLock(long maxWait) {
        long deadline = System.currentTimeMillis() + maxWait;
        while(System.currentTimeMillis() < deadline ) {
            if ( unsafe.compareAndSwapInt(null,addr,0,1)) {
                return true;
            }
        }
        return false;
    }

    public void unlock() {
        unsafe.putInt(addr,0);
    }

}
