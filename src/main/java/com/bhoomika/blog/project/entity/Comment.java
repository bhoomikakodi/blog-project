package com.bhoomika.blog.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(
        name="comments"
)
public class Comment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String body;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="post_id",
            nullable = false
    )
    private Post post;
}
