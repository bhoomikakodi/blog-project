package com.bhoomika.blog.project.payload;

import lombok.Data;

@Data
public class CommentDto {

    public Long id;
    private String name;
    private String email;
    private String body;
}
