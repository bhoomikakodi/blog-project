package com.bhoomika.blog.project.service;

import com.bhoomika.blog.project.payload.PostDto;
import com.bhoomika.blog.project.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);
   PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postdto, Long id);
    void deletePostById(Long id);
}
