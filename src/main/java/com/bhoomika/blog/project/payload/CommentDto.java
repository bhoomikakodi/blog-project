package com.bhoomika.blog.project.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    public Long id;
    @NotEmpty(message = "name should not be null or empty")
    private String name;


    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "body should not be empty")
    @Size(min = 10, message = "comment body must be minimum 10 character")
    private String body;
}
