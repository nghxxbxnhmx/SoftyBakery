package com.poly.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.models.Review;
import com.poly.services.ReviewService;

@CrossOrigin("*")
@RestController
public class ReviewController {
@Autowired
ReviewService rewDao;

@GetMapping("/rest/review")
public List<Review> getAll(Model model){
	return rewDao.findAll();
}
@GetMapping("/rest/review/{reviewId}")
public Review getOne(@PathVariable("reviewId") Integer reviewId) {
    return rewDao.findById(reviewId).get();
}

@PostMapping("/rest/review")
public Review post(@RequestBody Review review) {
    rewDao.save(review);
    return review;
}

@PutMapping("/rest/review/{reviewId}")
public Review put(@PathVariable("reviewId") Integer reviewId, @RequestBody Review review) {
    rewDao.save(review);
    return review;
}

@DeleteMapping("/rest/review/{reviewId}")
public void delete(@PathVariable("reviewId") Integer reviewId) {
    rewDao.deleteById(reviewId);
  
}
	
}
