package com.baeldung.daos;

import com.baeldung.controllers.PostController;
import com.baeldung.models.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class PostDao {

  private Logger logger = Logger.getLogger(PostController.class.getName());
  private SessionFactory sessionFactory;
  
  public PostDao() {}
  
  @Inject
  public PostDao(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
  }
  
  public Object add(Post post) {
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Object id = session.save(post);
      session.getTransaction().commit();
      session.close();
      return id;
  }
  
  public Post findById(int id) {
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Post post = (Post) session.get(Post.class, id);
      session.getTransaction().commit();
      session.close();
      return post;
  }
  
  
  public List<Post> all() {
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      List<Post> posts = (List<Post>) session.createQuery("FROM Post p").list();
      session.getTransaction().commit();
      session.close();
      return posts;
  }

}
