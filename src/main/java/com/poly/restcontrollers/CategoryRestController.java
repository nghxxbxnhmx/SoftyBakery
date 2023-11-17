package com.poly.restcontrollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.dao.*;
import com.poly.models.Category;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/category")
public class CategoryRestController {
	@Autowired
	ProductDAO pDAO;
	@Autowired
	CategoryDAO cDAO;

	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok(cDAO.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable("id") int id) {
		Category category = cDAO.findById(id).orElse(null);
		if(category==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(category);
	}
	@PostMapping()
	public ResponseEntity<Category> post(Category category) {
		if(cDAO.existsById(category.getCategoryId())) {
			return ResponseEntity.badRequest().build();
		}
		cDAO.save(category);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("id")
	public ResponseEntity<Category> delete(@PathVariable("id") String id) {
		if(!pDAO.existsById(Integer.parseInt(id))) {
			return ResponseEntity.notFound().build();
		}
		pDAO.deleteById(Integer.parseInt(id));
		return ResponseEntity.ok().build();
	}	
}
