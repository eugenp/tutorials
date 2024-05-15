package com.baeldung.view;

import com.baeldung.model.Post;
import com.blazebit.persistence.view.EntityView;

@EntityView(Post.class)
public interface PostWithAuthorView extends PostView {
    PersonView getAuthor();

}
