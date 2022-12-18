package com.baeldung.view;

import com.baeldung.model.Post;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

@EntityView(Post.class)
public interface PostView {

    @IdMapping
    Long getId();

    @Mapping("UPPER(title)")
    String getTitle();

    String getContent();
}
