package com.znwang;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * 测试：常用的一些Hash算法测试
 */
public class HashUtilsTest {

    private static final String WAIT_MD5_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";

    private static String FILE_NAME = "API_file.pdf";

    //同FILE_NAME为同一个文件，只是名字叫的不一样
    private static String FILE_NAME_2 = "API_file2.pdf";

    //同FILE_NAME为同一个文件，只是名字叫的不一样(且使用的是中文)
    private static String FILE_NAME_3 = "API接入指引.pdf";

    @Test
    public void md5() {
      byte [] bytes1 =  HashUtils.md5(WAIT_MD5_STRING);
      byte [] bytes2 =  HashUtils.md5(WAIT_MD5_STRING);
      System.out.println(new String(bytes1));
      System.out.println(new String(bytes2));
      System.out.println(new String(bytes1).equals(new String(bytes2)));
      System.out.println();
      for(int i =0 ;i< bytes1.length ; i++){
          System.out.println(bytes1[i] == bytes2[i]);
      }
    }

    @Test
    public void sha1() {
        byte [] bytes1 =  HashUtils.sha1(WAIT_MD5_STRING);
        byte [] bytes2 =  HashUtils.sha1(WAIT_MD5_STRING);
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public void sha256() {
        byte [] bytes1 =  HashUtils.sha256(WAIT_MD5_STRING);
        byte [] bytes2 =  HashUtils.sha256(WAIT_MD5_STRING);
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public  void sha512() {
        byte [] bytes1 =  HashUtils.sha512(WAIT_MD5_STRING);
        byte [] bytes2 =  HashUtils.sha512(WAIT_MD5_STRING);
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public void md51() {
        byte [] bytes1 =  HashUtils.md5(WAIT_MD5_STRING.getBytes());
        byte [] bytes2 =  HashUtils.md5(WAIT_MD5_STRING.getBytes());
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public void sha11() {
        byte [] bytes1 =  HashUtils.sha1(WAIT_MD5_STRING.getBytes());
        byte [] bytes2 =  HashUtils.sha1(WAIT_MD5_STRING.getBytes());
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public void sha2561() {
        byte [] bytes1 =  HashUtils.sha256(WAIT_MD5_STRING.getBytes());
        byte [] bytes2 =  HashUtils.sha256(WAIT_MD5_STRING.getBytes());
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    @Test
    public void sha5121() {
        byte [] bytes1 =  HashUtils.sha512(WAIT_MD5_STRING.getBytes());
        byte [] bytes2 =  HashUtils.sha512(WAIT_MD5_STRING.getBytes());
        System.out.println(new String(bytes1));
        System.out.println(new String(bytes2));
        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println();
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    //md5文件签名
    @Test
    public void md52() throws IOException {
        //获取文件路径
        String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        System.out.println("FILE_PATH:{}"+ filePath);
        String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        System.out.println("FILE_PATH2:{}"+ filePath2);
        String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        System.out.println("FILE_PATH3:{}"+ filePath3 +" decode_file_path3:{}" + decodeFilePath3);

        byte [] bytes1 =  HashUtils.md5(new File(filePath));
        byte [] bytes2 =  HashUtils.md5(new File(filePath));
        byte [] bytes3 =  HashUtils.md5(new File(filePath2));
        byte [] bytes4 =  HashUtils.md5(new File(decodeFilePath3));

        String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        System.out.println("encodeFileContent1:{}" + encodeFileContent1 + " " + encodeFileContent1.length());
        System.out.println("encodeFileContent2:{}" + encodeFileContent2);
        System.out.println("encodeFileContent3:{}" + encodeFileContent3);

        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println(new String(bytes1).equals(new String(bytes3)));
        System.out.println(new String(bytes1).equals(new String(bytes4)));
        System.out.println(new String(bytes2).equals(new String(bytes3)));
        System.out.println(new String(bytes2).equals(new String(bytes4)));
        System.out.println(new String(bytes4).equals(new String(bytes4)));

        System.out.println("\n===============");
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    //sha-1文件签名
    @Test
    public void sha12() throws IOException {
        //获取文件路径
        String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        System.out.println("FILE_PATH:{}"+ filePath);
        String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        System.out.println("FILE_PATH2:{}"+ filePath2);
        String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        System.out.println("FILE_PATH3:{}"+ filePath3 +" decode_file_path3:{}" + decodeFilePath3);

        byte [] bytes1 =  HashUtils.sha1(new File(filePath));
        byte [] bytes2 =  HashUtils.sha1(new File(filePath));
        byte [] bytes3 =  HashUtils.sha1(new File(filePath2));
        byte [] bytes4 =  HashUtils.sha1(new File(decodeFilePath3));

        String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        System.out.println("encodeFileContent1:{}" + encodeFileContent1);
        System.out.println("encodeFileContent2:{}" + encodeFileContent2);
        System.out.println("encodeFileContent3:{}" + encodeFileContent3);

        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println(new String(bytes1).equals(new String(bytes3)));
        System.out.println(new String(bytes1).equals(new String(bytes4)));
        System.out.println(new String(bytes2).equals(new String(bytes3)));
        System.out.println(new String(bytes2).equals(new String(bytes4)));
        System.out.println(new String(bytes4).equals(new String(bytes4)));

        System.out.println("\n===============");
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    //sha-256文件签名
    @Test
    public void sha2562() throws IOException {
        //获取文件路径
        String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        System.out.println("FILE_PATH:{}"+ filePath);
        String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        System.out.println("FILE_PATH2:{}"+ filePath2);
        String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        System.out.println("FILE_PATH3:{}"+ filePath3 +" decode_file_path3:{}" + decodeFilePath3);

        byte [] bytes1 =  HashUtils.sha256(new File(filePath));
        byte [] bytes2 =  HashUtils.sha256(new File(filePath));
        byte [] bytes3 =  HashUtils.sha256(new File(filePath2));
        byte [] bytes4 =  HashUtils.sha256(new File(decodeFilePath3));

        String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        System.out.println("encodeFileContent1:{}" + encodeFileContent1);
        System.out.println("encodeFileContent2:{}" + encodeFileContent2);
        System.out.println("encodeFileContent3:{}" + encodeFileContent3);

        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println(new String(bytes1).equals(new String(bytes3)));
        System.out.println(new String(bytes1).equals(new String(bytes4)));
        System.out.println(new String(bytes2).equals(new String(bytes3)));
        System.out.println(new String(bytes2).equals(new String(bytes4)));
        System.out.println(new String(bytes4).equals(new String(bytes4)));

        System.out.println("\n===============");
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }

    //sha-512文件签名
    @Test
    public void sha5122() throws IOException {
        //获取文件路径
        String filePath = this.getClass().getClassLoader().getResource(FILE_NAME).getPath();
        System.out.println("FILE_PATH:{}"+ filePath);
        String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        System.out.println("FILE_PATH2:{}"+ filePath2);
        String filePath3 = this.getClass().getClassLoader().getResource(FILE_NAME_3).getPath();
        String decodeFilePath3 = URLDecoder.decode(filePath3 , "UTF-8");
        System.out.println("FILE_PATH3:{}"+ filePath3 +" decode_file_path3:{}" + decodeFilePath3);

        byte [] bytes1 =  HashUtils.sha512(new File(filePath));
        byte [] bytes2 =  HashUtils.sha512(new File(filePath));
        byte [] bytes3 =  HashUtils.sha512(new File(filePath2));
        byte [] bytes4 =  HashUtils.sha512(new File(decodeFilePath3));

        String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        String encodeFileContent3 = Base64.getEncoder().encodeToString(bytes3);
        System.out.println("encodeFileContent1:{}" + encodeFileContent1);
        System.out.println("encodeFileContent2:{}" + encodeFileContent2);
        System.out.println("encodeFileContent3:{}" + encodeFileContent3);

        System.out.println(new String(bytes1).equals(new String(bytes2)));
        System.out.println(new String(bytes1).equals(new String(bytes3)));
        System.out.println(new String(bytes1).equals(new String(bytes4)));
        System.out.println(new String(bytes2).equals(new String(bytes3)));
        System.out.println(new String(bytes2).equals(new String(bytes4)));
        System.out.println(new String(bytes4).equals(new String(bytes4)));

        System.out.println("\n===============");
        for(int i =0 ;i< bytes1.length ; i++){
            System.out.println(bytes1[i] == bytes2[i]);
        }
    }


    /**
     * Java中取资源时，经常用到Class.getResource和ClassLoader.getResource.
     * 注意获取资源时，获取的是编译之后的class文件资源，而不会获取java源代码.
     */
    private void findFilePath(){
        // Class.getResource(String path)
        // path不以'/'开头时，默认是从此类所在的包下取资源；
        // path 以'/'开头时，则是从ClassPath根下获取；
        // 当前类(class)所在的包目录 (指定资源名，可以获取该包下面的其他资源)
        System.out.println(HashUtilsTest.class.getResource(""));
        // class path根目录 (指定资源名，可以获取根目录下面的资源)
        System.out.println(HashUtilsTest.class.getResource("/"));

        // Class.getResource和Class.getResourceAsStream在使用时，路径选择上是一样的。
        // getResourceAsStream()方法，它相当于你用getResource()取得File文件后，再new InputStream(file)一样的结果


        // Class.getClassLoader().getResource(String path)
        // path是从ClassPath根下获取
        System.out.println(HashUtilsTest.class.getClassLoader().getResource(""));
        // HashUtilsTest.class.getResource("/") == HashUtilsTest.class.getClassLoader().getResource("")
        // path不能以'/'开头
        System.out.println(HashUtilsTest.class.getClassLoader().getResource("/"));
    }

    /**
     * md5文件签名-zip
     * @throws IOException
     */
    @Test
    public void md524Zip() throws IOException {
        //String FILE_NAME_1 = "ab.zip";
        //String FILE_NAME_2 = "cd.zip";
        String FILE_NAME_1 = "WechatIMG384.jpeg";
        String FILE_NAME_2 = "WechatIMG385.jpeg";
        //获取文件路径
        String filePath = this.getClass().getClassLoader().getResource(FILE_NAME_1).getPath();
        System.out.println("FILE_NAME_1:{}"+ filePath);
        String filePath2 = this.getClass().getClassLoader().getResource(FILE_NAME_2).getPath();
        System.out.println("FILE_NAME_2:{}"+ filePath2);


        byte [] bytes1 =  HashUtils.md5(new File(filePath));
        byte [] bytes2 =  HashUtils.md5(new File(filePath2));

        String encodeFileContent1 = Base64.getEncoder().encodeToString(bytes1);
        String encodeFileContent2 = Base64.getEncoder().encodeToString(bytes2);
        System.out.println("encodeFileContent1:{}" + encodeFileContent1 + " " + encodeFileContent1.length());
        System.out.println("encodeFileContent2:{}" + encodeFileContent2);

        System.out.println(new String(bytes1).equals(new String(bytes2)));
    }
}