@org.hibernate.annotations.NamedEntityGraph(name = "package-post-with-comment-users", graph = "Post: subject, user, comments(user)")
package com.baeldung.hibernate.entitygraph.model;
