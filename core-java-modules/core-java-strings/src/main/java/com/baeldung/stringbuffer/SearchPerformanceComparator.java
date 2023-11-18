package com.baeldung.stringbuffer;

public class SearchPerformanceComparator {

    public static void searchString(String str) {
        for(int i = 0;i < str.length();i++)
            System.out.println(str.indexOf(str.charAt(i)));
    }
    
    public static void searchStringBuffer(StringBuffer str) {
        for(int i = 0;i < str.length();i++)
            System.out.println(str.indexOf(String.valueOf(str.charAt(i))));
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String str = new String("Two primary operations performed on StringBuffer are appending and inserting using the append() and insert() methods respectively. The append method adds the character sequence at the end of the string, and the insert method inserts the sequence of characters at the specified index. Both the methods are overloaded which allows us them to accept any data type and convert them to string before appending or inserting into the final string. StringBuffer also supports most methods available in String for comparing and searching.");
        StringBuffer sBuf = new StringBuffer("Two primary operations performed on StringBuffer are appending and inserting using the append() and insert() methods respectively. The append method adds the character sequence at the end of the string, and the insert method inserts the sequence of characters at the specified index. Both the methods are overloaded which allows us them to accept any data type and convert them to string before appending or inserting into the final string. StringBuffer also supports most methods available in String for comparing and searching.");

        searchString(str);
        System.out.println("search time string: "+(System.currentTimeMillis()-startTime)+"ms");
        startTime = System.currentTimeMillis();

        searchStringBuffer(sBuf);
        System.out.println("search time string: "+(System.currentTimeMillis()-startTime)+"ms");

    }
}
