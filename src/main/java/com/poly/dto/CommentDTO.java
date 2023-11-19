package com.poly.dto;

import com.poly.models.Comment;

import lombok.Data;

@Data
public class CommentDTO {
    private Comment parentComment;
    private Comment subComment;
}
