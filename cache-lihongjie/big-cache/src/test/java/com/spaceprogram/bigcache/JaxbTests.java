package com.spaceprogram.bigcache;

import com.spaceprogram.bigcache.marshallers.JAXBMarshaller;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: treeder
 * Date: Sep 30, 2008
 * Time: 6:04:51 PM
 */
public class JaxbTests extends S3CacheTests{

    @BeforeClass
    public static void setupCache() throws IOException {
        System.out.println("Setting up caches in JaxbTests");
        Properties props = new Properties();
        InputStream is = S3CacheTests.class.getResourceAsStream("/aws-auth.properties");
        if(is == null){
            throw new RuntimeException("No aws-auth.properties file found.");
        }
        props.load(is);
        s3cache = new S3Cache(props.getProperty("accessKey"), props.getProperty("secretKey"), props.getProperty("bucketName"));
        s3cache.setMarshaller(new JAXBMarshaller());
    }

    @Test
    public void testSet() throws Exception {
        super.testSet();
    }

    
    @Test
    public void testEnums() throws Exception {
        String key = "class-with-enums-id";
        ClassWithEnums classWithEnums = new ClassWithEnums();
        classWithEnums.setId("someid");
        classWithEnums.setDate("abc");
        classWithEnums.setSomeEnum(SomeEnum.type3);
        s3cache.put(key, classWithEnums, 3600);
        ClassWithEnums ret = (ClassWithEnums) s3cache.get(key);
        Assert.assertEquals(classWithEnums.getSomeEnum(), ret.getSomeEnum());
    }


}
