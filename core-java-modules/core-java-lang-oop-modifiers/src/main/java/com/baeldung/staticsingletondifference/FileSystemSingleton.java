package com.baeldung.staticsingletondifference;

public class FileSystemSingleton implements SingletonInterface{

    private int filesWritten;

    private FileSystemSingleton() {
    }

    private static class SingletonHolder {
        public static final FileSystemSingleton instance = new FileSystemSingleton();
    }

    public static FileSystemSingleton getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public String describeMe() {
        return "File System Responsibilities";
    }

    @Override
    public String passOnLocks(MyLock lock) {
        return lock.takeLock(2);
    }

    @Override
    public void increment() {
        this.filesWritten++;
    }

    public int getFilesWritten() {
        return filesWritten;
    }
}
