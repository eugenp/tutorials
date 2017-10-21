//package com.baeldung.bizzkit;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ComponentScan(value="com.baeldung.bizzkit")
//public class BizzkitApplicationTests {
//
//    private AnnotationConfigApplicationContext context = null;
//
//    @Bean
//    public BusinessService getBusinessService() {
//        return new BusinessService();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        context = new AnnotationConfigApplicationContext(BizzkitApplicationTests.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        context.close();
//    }
//
//    @Test
//    public void Given_BusinessTypeBeanIsInitialized_When_BusinessSetterBeanIsInitialized_Then_ShouldPrintBusinessSetterBeanDetailsWithBusinessTypeDetails() {
//        BusinessSetterBean business = context.getBean(BusinessSetterBean.class);
//        business.setId(20);
//        business.setName("Baeldung LLC");
//
//        String expected = "Business ID => 20\nBusiness Name => Baeldung LLC";
//        Assert.assertEquals(expected, business.toString());
//    }
//    
//    @Test
//    public void Given_BusinessTypeBeanIsInitialized_When_BusinessConstructorBeanIsInitialized_Then_ShouldPrintBusinessSetterBeanDetailsWithBusinessTypeDetails() {
//        BusinessConstructorBean business = context.getBean(BusinessConstructorBean.class);
//        business.setId(50);
//        business.setName("BaeldungWeb LLC");
//
//        String expected = "Business ID => 50\nBusiness Name => BaeldungWeb LLC";
//        Assert.assertEquals(expected, business.toString());
//    }
//
//}
