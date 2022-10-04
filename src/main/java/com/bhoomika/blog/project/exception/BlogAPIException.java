package com.bhoomika.blog.project.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BlogAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
