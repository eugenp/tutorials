//package footmark.springdata.jpa.domain.domain2;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//public class Post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "post", orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<Comment>();
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void addComment(Comment comment) {
//        comments.add(comment);
//        comment.setPost(this);
//    }
//
//    public void removeComment(Comment comment) {
//        comment.setPost(null);
//        this.comments.remove(comment);
//    }
//}
