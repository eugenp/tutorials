//package com.baeldung.jaxrs;
//
//
//import java.util.List;
//
//import com.baeldung.Post;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//
//@Path("jax-rs/consume-posts")
//public class JaxRsPostResource {
//
//    @Inject
//    JaxRsPostService jaxRsPostService;
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//    public List<Post> getAllPosts() {
//        return jaxRsPostService.getPosts();
//    }
//}
