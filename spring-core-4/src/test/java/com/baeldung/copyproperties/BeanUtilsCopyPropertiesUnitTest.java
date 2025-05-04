package com.baeldung.copyproperties;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BeanUtilsCopyPropertiesUnitTest {

    @Test
    public void givenObjects_whenUsingIgnoreProperties_thenCopyProperties() {
        SourceBean sourceBean = new SourceBean("Peter", 30, "LA");
        TargetBean targetBean = new TargetBean();

        BeanUtils.copyProperties(sourceBean, targetBean, "address");
        assertEquals(targetBean.getName(), sourceBean.getName());
        assertEquals(targetBean.getAge(), sourceBean.getAge());
        assertNull(targetBean.getAddress());
    }

    @Test
    public void givenObjects_whenUsingIntermediateObject_thenCopyProperties() {
        SourceBean sourceBean = new SourceBean("Peter", 30, "LA");
        TempDTO tempDTO = new TempDTO();
        BeanUtils.copyProperties(sourceBean, tempDTO);

        TargetBean targetBean = new TargetBean();
        BeanUtils.copyProperties(tempDTO, targetBean);
        assertEquals(targetBean.getName(), sourceBean.getName());
        assertEquals(targetBean.getAge(), sourceBean.getAge());
        assertNull(targetBean.getAddress());
    }

    @Test
    public void givenObjects_whenUsingCustomWrapper_thenCopyProperties() {
        SourceBean sourceBean = new SourceBean("Peter", 30, "LA");
        TargetBean targetBean = new TargetBean();
        BeanUtilsCopyProperties.copySelectedPropertiesUsingCustomWrapper(sourceBean, targetBean, new HashSet<>(Arrays.asList("name", "age")));
        System.out.println(targetBean);
        assertEquals(targetBean.getName(), sourceBean.getName());
        assertEquals(targetBean.getAge(), sourceBean.getAge());
        assertNull(targetBean.getAddress());
    }
}
