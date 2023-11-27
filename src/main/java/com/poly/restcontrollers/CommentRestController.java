package com.poly.restcontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.CommentDAO;
import com.poly.dao.ProductDAO;
import com.poly.dto.CommentDTO;
import com.poly.models.Account;
import com.poly.models.Comment;
import com.poly.models.Product;
import com.poly.services.AccountService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/comment")
public class CommentRestController {
    @Autowired
    CommentDAO cmtDAO;
    @Autowired
    ProductDAO pDAO;
    @Autowired
    AccountService accountService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Comment> findAll() {
        return cmtDAO.findAll();
    }

    // @GetMapping("/{id}")
    // public CommentDTO findById(@PathVariable int id) {
    // CommentDTO cmtDTO = new CommentDTO();
    // cmtDTO.setComment(cmtDAO.findById(id).get());
    // cmtDTO.setSubComment(cmtDAO.findSubCommentsById(id));
    // return cmtDTO;
    // }

    @GetMapping("/{productId}")
    public List<CommentDTO> findByProductId(@PathVariable int productId) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        Product product = pDAO.findById(productId).get();

        for (Comment cmt : cmtDAO.findParentCommentByProductId(product)) {
            CommentDTO cmtDTO = new CommentDTO();
            cmtDTO.setParentComment(cmt);
            cmtDTO.setSubComment(cmtDAO.findSubCommentsByProductId(product, cmt.getCommentId()));

            commentDTOs.add(cmtDTO);
        }
        return commentDTOs;
    }

    @PostMapping()
    public void postComment(@RequestBody Comment comment) throws IOException {
        Comment commentPost = new Comment();
        commentPost.setAccount(getAccountAuth());
        commentPost.setProduct(pDAO.findById(comment.getProduct().getProductId()).get());
        commentPost.setCommentContent(comment.getCommentContent());
        commentPost.setCommentDate(comment.getCommentDate());
        commentPost.setParentCommentId(comment.getParentCommentId());
        cmtDAO.save(commentPost);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") int commentId) {
        Comment parentComment = cmtDAO.findById(commentId).get();
        List<Comment> subCommentList = cmtDAO.findSubCommentsByProductId(parentComment.getProduct(), commentId);
        for(Comment subComment : subCommentList) {
            cmtDAO.delete(subComment);
        }
        cmtDAO.delete(parentComment);
    }

    public Account getAccountAuth() {
        return accountService.getAccountAuth();
    }
}
