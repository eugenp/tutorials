package com.baeldung.jna;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

public interface StdC extends Library {
    StdC INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c", StdC.class );
    Pointer malloc(long n);
    void free(Pointer p);
    Pointer memset(Pointer p, int c, long n);
    int open(String path, int flags) throws LastErrorException;
    int close(int fd) throws LastErrorException;
}

