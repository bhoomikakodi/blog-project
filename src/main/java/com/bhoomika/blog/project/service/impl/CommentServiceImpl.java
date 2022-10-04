package com.bhoomika.blog.project.service.impl;

import com.bhoomika.blog.project.entity.Comment;
import com.bhoomika.blog.project.entity.Post;
import com.bhoomika.blog.project.exception.BlogAPIException;
import com.bhoomika.blog.project.exception.ResourceNotFoundException;
import com.bhoomika.blog.project.payload.CommentDto;
import com.bhoomika.blog.project.repository.CommentRepository;
import com.bhoomika.blog.project.repository.PostRepository;
import com.bhoomika.blog.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    //convert Entity to DTO

    //Manually
    private CommentDto mapToDto(Comment comment){
//        CommentDto commentDto= new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
//
        return commentDto;
    }

//using model mapper


    //convert DTO to Entity


    private  Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comment by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment to list of commentDto
        return (List<CommentDto>) comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new RuntimeException("comment does not belong to the post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentUpdate) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new RuntimeException("comment does not belong to post");
        }
        comment.setName(commentUpdate.getName());
        comment.setEmail(commentUpdate.getEmail());
        comment.setBody(commentUpdate.getBody());

        Comment commentUpdated = commentRepository.save(comment);


        return mapToDto(commentUpdated);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }
}
