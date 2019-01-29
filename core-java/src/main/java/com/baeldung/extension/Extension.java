package com.baeldung.extension;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author zn.wang
 */
public class Extension {

    /**
     * 获取文件的扩展明
     * Instead of file name we can also specify full path of a file eg. /baeldung/com/demo/abc.java
     * @param filename
     * @return
     */
    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 使用过滤的方式，获取文件的扩展
     * @param filename
     * @return
     */
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional
                .ofNullable(filename)
                .filter(new Predicate<String>() {
                           @Override
                           public boolean test(String f) {
                               return f.contains(".");
                           }
                })
                .map(new Function<String, String>() {
                          @Override
                          public String apply(String f) {
                              return f.substring(filename.lastIndexOf(".") + 1);
                          }
                });
    }

    /**
     * 使用guava的{@link com.google.common.io.Files#getFileExtension(String)}获取文件的扩展
     * @param filename
     * @return
     */
    public String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }
}
