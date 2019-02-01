package com.znwang;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * 测试：常用的一些Hash算法测试
 */
public class HashUtilsUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashUtilsUnitTest.class);

    private static final String WAIT_MD5_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";

    private static final String FILE_NAME = "API_file.pdf";

    //同FILE_NAME为同一个文件，只是名字叫的不一样
    private static final String FILE_NAME_2 = "API_file2.pdf";

    //同FILE_NAME为同一个文件，只是名字叫的不一样(且使用的是中文)
    private static final String FILE_NAME_3 = "API接入指引.pdf";


    @Test
    public void md5() {
        final byte [] bytes1 =  HashUtils.md5(WAIT_MD5_STRING);
        final byte [] bytes2 =  HashUtils.md5(WAIT_MD5_STRING);

        LOGGER.info("bytes1:{}" , new String(bytes1));//NOPMD
        LOGGER.info("bytes2:{}" , new String(bytes2));

        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void sha1() {
        final byte [] bytes1 =  HashUtils.sha1(WAIT_MD5_STRING);
        final byte [] bytes2 =  HashUtils.sha1(WAIT_MD5_STRING);

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void sha256() {
        final byte [] bytes1 =  HashUtils.sha256(WAIT_MD5_STRING);
        final byte [] bytes2 =  HashUtils.sha256(WAIT_MD5_STRING);

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public  void sha512() {
        final byte [] bytes1 =  HashUtils.sha512(WAIT_MD5_STRING);
        final byte [] bytes2 =  HashUtils.sha512(WAIT_MD5_STRING);

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void md51() {
        final byte [] bytes1 =  HashUtils.md5(WAIT_MD5_STRING.getBytes());
        final byte [] bytes2 =  HashUtils.md5(WAIT_MD5_STRING.getBytes());

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void sha11() {
        final byte [] bytes1 =  HashUtils.sha1(WAIT_MD5_STRING.getBytes());
        final byte [] bytes2 =  HashUtils.sha1(WAIT_MD5_STRING.getBytes());

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void sha2561() {
        final byte [] bytes1 =  HashUtils.sha256(WAIT_MD5_STRING.getBytes());
        final byte [] bytes2 =  HashUtils.sha256(WAIT_MD5_STRING.getBytes());

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    @Test
    public void sha5121() {
        final byte [] bytes1 =  HashUtils.sha512(WAIT_MD5_STRING.getBytes());
        final byte [] bytes2 =  HashUtils.sha512(WAIT_MD5_STRING.getBytes());

        LOGGER.info("bytes1:{}" , new String(bytes1));
        LOGGER.info("bytes2:{}" , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes2));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    //md5文件签名
    @Test
    public void md52() throws IOException {
        //获取文件路径
        final String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        LOGGER.info("FILE_PATH:{}" , filePath);
        final String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        LOGGER.info("FILE_PATH2:{}" , filePath2);
        final String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        final String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        LOGGER.info("FILE_PATH3:{},decode_file_path3:{}" , filePath3 , decodeFilePath3);

        final byte [] bytes1 =  HashUtils.md5(new File(filePath));
        final byte [] bytes2 =  HashUtils.md5(new File(filePath));
        final byte [] bytes3 =  HashUtils.md5(new File(filePath2));
        final byte [] bytes4 =  HashUtils.md5(new File(decodeFilePath3));

        final String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        final String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        final String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        LOGGER.info("encodeFileContent1:{}, length:{}" , encodeFileContent1 , encodeFileContent1.length());
        LOGGER.info("encodeFileContent2:{}" , encodeFileContent2);
        LOGGER.info("encodeFileContent3:{}" , encodeFileContent3);

        Assert.assertEquals(new String(bytes1) , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes3));
        Assert.assertEquals(new String(bytes1) , new String(bytes4));
        Assert.assertEquals(new String(bytes2) , new String(bytes3));
        Assert.assertEquals(new String(bytes2) , new String(bytes4));
        Assert.assertEquals(new String(bytes4) , new String(bytes4));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    //sha-1文件签名
    @Test
    public void sha12() throws IOException {
        //获取文件路径
        final String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        LOGGER.info("FILE_PATH:{}" , filePath);
        final String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        LOGGER.info("FILE_PATH2:{}" , filePath2);
        final String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        final String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        LOGGER.info("FILE_PATH3:{},decode_file_path3:{}" , filePath3 , decodeFilePath3);

        final byte [] bytes1 =  HashUtils.sha1(new File(filePath));
        final byte [] bytes2 =  HashUtils.sha1(new File(filePath));
        final byte [] bytes3 =  HashUtils.sha1(new File(filePath2));
        final byte [] bytes4 =  HashUtils.sha1(new File(decodeFilePath3));

        final String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        final String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        final String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        LOGGER.info("encodeFileContent1:{}, length:{}" , encodeFileContent1 , encodeFileContent1.length());
        LOGGER.info("encodeFileContent2:{}" , encodeFileContent2);
        LOGGER.info("encodeFileContent3:{}" , encodeFileContent3);

        Assert.assertEquals(new String(bytes1) , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes3));
        Assert.assertEquals(new String(bytes1) , new String(bytes4));
        Assert.assertEquals(new String(bytes2) , new String(bytes3));
        Assert.assertEquals(new String(bytes2) , new String(bytes4));
        Assert.assertEquals(new String(bytes4) , new String(bytes4));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    //sha-256文件签名
    @Test
    public void sha2562() throws IOException {
        //获取文件路径
        final String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        LOGGER.info("FILE_PATH:{}" , filePath);
        final String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        LOGGER.info("FILE_PATH2:{}" , filePath2);
        final String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        final String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        LOGGER.info("FILE_PATH3:{},decode_file_path3:{}" , filePath3 , decodeFilePath3);

        final byte [] bytes1 =  HashUtils.sha256(new File(filePath));
        final byte [] bytes2 =  HashUtils.sha256(new File(filePath));
        final byte [] bytes3 =  HashUtils.sha256(new File(filePath2));
        final byte [] bytes4 =  HashUtils.sha256(new File(decodeFilePath3));

        final String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        final String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        final String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        LOGGER.info("encodeFileContent1:{}, length:{}" , encodeFileContent1 , encodeFileContent1.length());
        LOGGER.info("encodeFileContent2:{}" , encodeFileContent2);
        LOGGER.info("encodeFileContent3:{}" , encodeFileContent3);

        Assert.assertEquals(new String(bytes1) , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes3));
        Assert.assertEquals(new String(bytes1) , new String(bytes4));
        Assert.assertEquals(new String(bytes2) , new String(bytes3));
        Assert.assertEquals(new String(bytes2) , new String(bytes4));
        Assert.assertEquals(new String(bytes4) , new String(bytes4));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }

    //sha-512文件签名
    @Test
    public void sha5122() throws IOException {
        //获取文件路径
        final String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        LOGGER.info("FILE_PATH:{}" , filePath);
        final String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        LOGGER.info("FILE_PATH2:{}" , filePath2);
        final String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        final String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        LOGGER.info("FILE_PATH3:{},decode_file_path3:{}" , filePath3 , decodeFilePath3);

        final byte [] bytes1 =  HashUtils.sha512(new File(filePath));
        final byte [] bytes2 =  HashUtils.sha512(new File(filePath));
        final byte [] bytes3 =  HashUtils.sha512(new File(filePath2));
        final byte [] bytes4 =  HashUtils.sha512(new File(decodeFilePath3));

        final String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        final String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        final String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        LOGGER.info("encodeFileContent1:{}, length:{}" , encodeFileContent1 , encodeFileContent1.length());
        LOGGER.info("encodeFileContent2:{}" , encodeFileContent2);
        LOGGER.info("encodeFileContent3:{}" , encodeFileContent3);

        Assert.assertEquals(new String(bytes1) , new String(bytes2));
        Assert.assertEquals(new String(bytes1) , new String(bytes3));
        Assert.assertEquals(new String(bytes1) , new String(bytes4));
        Assert.assertEquals(new String(bytes2) , new String(bytes3));
        Assert.assertEquals(new String(bytes2) , new String(bytes4));
        Assert.assertEquals(new String(bytes4) , new String(bytes4));

        for(int i =0 ;i< bytes1.length ; i++){
            Assert.assertEquals(bytes1[i] , bytes2[i]);
        }
    }


    /**
     * Java中取资源时，经常用到Class.getResource和ClassLoader.getResource.
     * 注意获取资源时，获取的是编译之后的class文件资源，而不会获取java源代码.
     */
    public void findFilePath(){
        // 一：Class.getResource(String path)
        // path不以'/'开头时，默认是从此类所在的包下取资源；
        // path 以'/'开头时，则是从ClassPath根下获取；
        // 当前类(class)所在的包目录 (指定资源名，可以获取该包下面的其他资源)
        //System.out.println(HashUtilsTest.class.getResource(""));
        LOGGER.info("HashUtilsTest.class.getResource(\"\"):{}" , HashUtilsUnitTest.class.getResource(""));
        // class path根目录 (指定资源名，可以获取根目录下面的资源)
        //System.out.println(HashUtilsTest.class.getResource("/"));
        LOGGER.info("HashUtilsTest.class.getResource(\"/\"):{}" , HashUtilsUnitTest.class.getResource(""));

        // Class.getResource和Class.getResourceAsStream在使用时，路径选择上是一样的。
        // getResourceAsStream()方法，它相当于你用getResource()取得File文件后，再new InputStream(file)一样的结果


        // 二：Class.getClassLoader().getResource(String path)
        // path是从ClassPath根下获取
        //System.out.println(HashUtilsTest.class.getClassLoader().getResource(""));
        LOGGER.info("HashUtilsTest.class.getClassLoader().getResource(\"\"):{}" , HashUtilsUnitTest.class.getClassLoader().getResource(""));
        // HashUtilsTest.class.getResource("/") == HashUtilsTest.class.getClassLoader().getResource("")
        // path不能以'/'开头
        //System.out.println(HashUtilsTest.class.getClassLoader().getResource("/"));
        LOGGER.info("HashUtilsTest.class.getClassLoader().getResource(\"/\"):{}" , HashUtilsUnitTest.class.getClassLoader().getResource("/"));
    }

    /**
     * md5文件签名-zip
     * @throws IOException
     */
    @Test
    public void md524Zip() throws IOException {
        findFilePath();
        //String fileName1 = "ab.zip";
        //String fileName2 = "cd.zip";
        String fileName1 = "WechatIMG384.jpeg";
        String fileName2 = "WechatIMG385.jpeg";
        //获取文件路径
        final String filePath = this.getClass().getClassLoader().getResource(fileName1).getPath();
        LOGGER.info("fileName1:{}" , filePath);
        final String filePath2 = this.getClass().getClassLoader().getResource(fileName2).getPath();
        LOGGER.info("fileName2:{}" , filePath);

        final byte [] bytes1 =  HashUtils.md5(new File(filePath));
        final byte [] bytes2 =  HashUtils.md5(new File(filePath2));

        final String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        final String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        LOGGER.info("encodeFileContent1:{}, length:{}" , encodeFileContent1 , encodeFileContent1.length());
        LOGGER.info("encodeFileContent2:{}" , encodeFileContent2);

        Assert.assertNotEquals(new String(bytes1) , new String(bytes2));
    }
}