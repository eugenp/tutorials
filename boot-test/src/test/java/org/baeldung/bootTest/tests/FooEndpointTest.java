//package org.baeldung.bootTest.tests;
//
//import static org.mockito.Matchers.anyLong;
//import static org.mockito.Mockito.doCallRealMethod;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.baeldung.bootTest.service.FooService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(FooService.class)
//public class FooEndpointTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private FooService fooService;
//
//    @Test
//    public void getFooShouldReturnFoo() throws Exception {
////        given(this.userVehicleService.getFooWithId(1l))
////            .willReturn(new ResponseEntity<Foo>( new Foo(1l, "Foo_Name"),HttpStatus.ACCEPTED));
//        
//        doCallRealMethod().when(fooService).getFooWithId(anyLong());
//
//        this.mvc.perform(get("/{id}")
//            .accept(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(status().isOk())
//            .andExpect(content().string("Foo_Name"));
//    }
//
//}