package com.baeldung.grep;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unix4j.Unix4j;
import static org.unix4j.Unix4j.*;
import static org.junit.Assert.assertEquals;
import org.unix4j.line.Line;
import static org.unix4j.unix.Grep.*;
import static org.unix4j.unix.cut.CutOption.*;

/**
 * 使用
 * unix4j-base-0.4.jar
 * unix4j-command-0.4.jar
 */
public class GrepWithUnix4JIntegrationTest {

    private File fileToGrep;

    @Before
    public void init() {
        final String separator = File.separator;
        final String filePath = String.join(separator, new String[] { "src", "test", "resources", "dictionary.in" });
        Assert.assertEquals("src/test/resources/dictionary.in" , filePath);
        fileToGrep = new File(filePath);
    }

    /**
     * 测试linux的grep命令
     */
    @Test
    public void whenGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 4;

        // grep "NINETEEN" dictionary.txt
        List<Line> lines = Unix4j.grep("NINETEEN", fileToGrep).toLineList();
        for(Line line : lines){
            System.out.println("line:{}" + line.toString());
        }
        assertEquals(expectedLineCount, lines.size());
    }

    /**
     * grep -v "NINETEEN" dictionary.txt
     * 反转查询：查询除了NINETEEN之外的所有
     */
    @Test
    public void whenInverseGrepWithSimpleString_thenCorrect() {
        int expectedLineCount = 178687;
        // grep -v "NINETEEN" dictionary.txt
        List<Line> lines = grep(Options.v, "NINETEEN", fileToGrep).toLineList();

        assertEquals(expectedLineCount, lines.size());

        System.out.println("=============检测一下是否为反转查询==================");
        List<String> linesStr= Lists.newArrayList();
        linesStr.add("NINETEEN");
        linesStr.add("NINETEENS");
        linesStr.add("NINETEENTH");
        linesStr.add("NINETEENTHS");
        for(Line line : lines){
            if(linesStr.contains(line.toString())){
                System.out.println("linesStr is contains " + line.toString());
            }
        }
    }

    /**
     * 统计文件或者文本中包含匹配字符串的行数 -c 选项：
     * grep -c "text" file_name
     *
     * 例如：grep -c ".*?NINE.*?" dictionary.txt
     */
    @Test
    public void whenGrepWithRegex_thenCorrect() {
        int expectedLineCount = 151;

        // grep -c ".*?NINE.*?" dictionary.txt
        String patternCount =
                grep(Options.c, ".*?NINE.*?", fileToGrep)
                .cut(fields, ":", 1)
                .toStringResult();

        assertEquals(expectedLineCount, Integer.parseInt(patternCount));
    }
}
