//package com.baeldung.jaxrs;
//
//import java.util.List;
//
//import com.baeldung.Post;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.ws.rs.client.Client;
//import jakarta.ws.rs.client.ClientBuilder;
//import jakarta.ws.rs.client.WebTarget;
//import jakarta.ws.rs.core.GenericType;
//
//@ApplicationScoped
//public class JaxRsPostService {
//
//    private final Client client;
//    private final WebTarget target;
//
//    public JaxRsPostService() {
//        this.client = ClientBuilder.newClient();
//        this.target = client.target("http://localhost:8080/posts");
//    }
//
//    public List<Post> getPosts() {
//        return target.request()
//          .get(new GenericType<List<Post>>() {
//          });
//    }
//}
