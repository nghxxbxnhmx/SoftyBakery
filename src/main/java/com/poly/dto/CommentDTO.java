package com.poly.dto;

import java.util.List;

import com.poly.models.Comment;

import lombok.Data;

@Data
public class CommentDTO {
    private Comment parentComment;
    private List<Comment> subComment;
}
