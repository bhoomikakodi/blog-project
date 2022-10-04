package com.bhoomika.blog.project.service;

import com.bhoomika.blog.project.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentUpdate);
    void deleteComment(Long postId,Long commentId);

}
