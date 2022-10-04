package com.bhoomika.blog.project.service.impl;

import com.bhoomika.blog.project.entity.Post;
import com.bhoomika.blog.project.payload.PostDto;
import com.bhoomika.blog.project.repository.PostRepository;
import com.bhoomika.blog.project.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;



private PostDto mapToDto(Post post){
//    PostDto postDto = new PostDto();
//    postDto.setId(post.getId());
//    postDto.setTitle(post.getTitle());
//    postDto.setDescription(post.getDescription());
//    postDto.setContent(post.getContent());
    PostDto postDto = modelMapper.map(post, PostDto.class);
    return postDto;
}


private Post mapToEntity(PostDto postDto){
//    Post post = new Post();
//    post.setTitle(postDto.getTitle());
//    post.setDescription(postDto.getDescription());
//    post.setContent(postDto.getContent());

    Post post = modelMapper.map(postDto, Post.class);
    return post;
}

    @Override
    public PostDto createPost(PostDto postDto) {

    Post post = mapToEntity(postDto);

    Post newPost = postRepository.save(post);

    PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(pos -> mapToDto(pos)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postdto, Long id) {

    Post post = postRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("resource not found"));

    post.setTitle(postdto.getTitle());
    post.setDescription(postdto.getDescription());
    post.setContent(postdto.getContent());

    Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {

    Post post = postRepository
            .findById(id)
            .orElseThrow(()-> new RuntimeException("resource not found"));

    postRepository.delete(post);

    }
}
