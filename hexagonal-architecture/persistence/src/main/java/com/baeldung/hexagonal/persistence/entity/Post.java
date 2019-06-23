package com.baeldung.hexagonal.persistence.entity;

import com.baeldung.hexagonal.core.domain.bo.PostBo.PostState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_posts")
@Data
@NoArgsConstructor
public class Post extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private PostState state;
}
