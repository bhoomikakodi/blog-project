package com.bhoomika.blog.project.service;

import com.bhoomika.blog.project.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postdto, Long id);
    void deletePostById(Long id);
}
