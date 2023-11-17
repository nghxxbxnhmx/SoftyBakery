package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.models.Comment;
import com.poly.models.Product;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer> {
    // @Query("SELECT cmt FROM Comment cmt WHERE cmt.parentCommentId = ?1")
    // public List<Comment> findSubCommentsById(int parentCommentId);

    @Query("SELECT cmt FROM Comment cmt WHERE cmt.product = ?1 AND cmt.parentCommentId = 0")
    public List<Comment> findParentCommentByProductId(Product product);
    
    @Query("SELECT cmt FROM Comment cmt WHERE cmt.product = ?1 AND cmt.parentCommentId = ?2")
    public List<Comment> findSubCommentsByProductId(Product product, int parentCommentId);
    
    
}
