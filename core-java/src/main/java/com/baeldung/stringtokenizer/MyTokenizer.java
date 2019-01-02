package com.baeldung.stringtokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * 测试：String分词器
 */
public class MyTokenizer {

    /**
     * 将一个字符串，按照固定的分词器(比如：英文逗号)进行拆解，返回List集合
     * @param str
     * @return
     */
    public List<String> getTokens(String str) {
        List<String> tokens = new ArrayList<>();
        // StringTokenizer tokenizer = new StringTokenizer( str );
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        // StringTokenizer tokenizer = new StringTokenizer( str , "," , true );
        while (tokenizer.hasMoreElements()) {
            tokens.add(tokenizer.nextToken());
            // tokens.add( tokenizer.nextToken("e") );
        }
        int tokenLength = tokens.size();
        return tokens;
    }

    /**
     * 将一个字符串，按照固定的分词器(比如：英文逗号)进行拆解，返回List集合
     * 注意：使用到了Collections.list工具类
     * @param str
     * @return
     */
    public List<String> getTokensWithCollection(String str) {
        List<String> list = new ArrayList<>();
        for (Object token : Collections.list(new StringTokenizer(str, ","))) {
            String s = (String) token;
            list.add(s);
        }
        return list;
    }

    /**
     * 将一个文件中的语句，按照分词器进行拆解，返回List集合
     * @param path
     * @param delim
     * @return
     */
    public List<String> getTokensFromFile(String path, String delim) {
        List<String> tokens = new ArrayList<>();
        String currLine;
        StringTokenizer tokenizer;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(MyTokenizer.class.getResourceAsStream("/" + path)))) {
            while ((currLine = br.readLine()) != null) {
                tokenizer = new StringTokenizer(currLine, delim);
                while (tokenizer.hasMoreElements()) {
                    tokens.add(tokenizer.nextToken());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

}
