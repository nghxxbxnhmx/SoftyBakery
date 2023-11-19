package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.CommentDAO;
import com.poly.dto.CommentDTO;
import com.poly.models.Comment;
@CrossOrigin("*")
@RestController
public class CommentRestController {
	@Autowired
	CommentDAO cmDao;
	@GetMapping("/rest/comments")
	public List<CommentDTO> getAll(Model model){
		List<CommentDTO> commentDTOs = new ArrayList<>();
		for(Comment comment: cmDao.findAll()) {
			CommentDTO cmtDTO = new CommentDTO();
			cmtDTO.setSubComment(comment);
			if(comment.getParentCommentId()!=0) {
				cmtDTO.setParentComment(cmDao.findById(comment.getParentCommentId()).get());
			}
			commentDTOs.add(cmtDTO);
		}
		return commentDTOs;
	}
	@GetMapping("/rest/comments/{commentId}")
	public Comment getOne(@PathVariable("commentId") Integer commentId) {
	    return cmDao.findById(commentId).get();
	}
	@DeleteMapping("/rest/comments/{commentId}")
	public void delete(@PathVariable("commentId") Integer commentId) {
		cmDao.deleteById(commentId);
	}
}
