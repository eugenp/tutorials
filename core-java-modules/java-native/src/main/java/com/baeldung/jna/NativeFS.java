package com.baeldung.jna;

import java.util.Collections;
import java.util.Map;

import com.sun.jna.FunctionMapper;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

public interface NativeFS extends Library {
    
    FunctionMapper mapper = (library,method) -> {
        if (Platform.isWindows()) {
            return "_" + method.getName();
        }
        else {
            return "__x" + method.getName(); // On Linux, stat is actually _xstat
        }
    };
    
    public NativeFS INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c",
      NativeFS.class,
      Collections.singletonMap(Library.OPTION_FUNCTION_MAPPER, mapper));

    int stat(String path, Stat stat) throws LastErrorException;

    @FieldOrder({"st_dev","st_ino","st_mode","st_nlink","st_uid","st_gid","st_rdev","st_size","st_atime","st_mtime","st_ctime"})
    public class Stat extends Structure {
        public int st_dev;
        public int st_ino;
        public short st_mode;
        public short st_nlink;
        public short st_uid;
        public short st_gid;
        public int st_rdev;
        public NativeLong st_size;
        public NativeLong st_atime;
        public NativeLong st_mtime;
        public NativeLong st_ctime;
    }
}
