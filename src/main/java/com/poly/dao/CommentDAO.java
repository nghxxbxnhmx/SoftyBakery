package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.models.Comment;


public interface CommentDAO extends JpaRepository<Comment, Integer> {

}
