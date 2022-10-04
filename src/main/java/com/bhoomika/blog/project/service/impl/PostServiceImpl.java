package com.bhoomika.blog.project.service.impl;

import com.bhoomika.blog.project.entity.Post;
import com.bhoomika.blog.project.exception.ResourceNotFoundException;
import com.bhoomika.blog.project.payload.PostDto;
import com.bhoomika.blog.project.payload.PostResponse;
import com.bhoomika.blog.project.repository.PostRepository;
import com.bhoomika.blog.project.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    private PostDto mapToDto(Post post) {
//    PostDto postDto = new PostDto();
//    postDto.setId(post.getId());
//    postDto.setTitle(post.getTitle());
//    postDto.setDescription(post.getDescription());
//    postDto.setContent(post.getContent());
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }


    private Post mapToEntity(PostDto postDto) {
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;



    }


    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id", id ));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postdto, Long id) {

        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id", id));

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
                .orElseThrow(() -> new ResourceNotFoundException("Post","id", id));

        postRepository.delete(post);

    }
}
