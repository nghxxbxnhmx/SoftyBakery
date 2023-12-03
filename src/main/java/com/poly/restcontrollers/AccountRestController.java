package com.poly.restcontrollers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.AccountDAO;
import com.poly.dto.NameDTO;
import com.poly.models.Account;
import com.poly.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/account")
public class AccountRestController {
	@Autowired
	AccountDAO aDAO;
	@Autowired
	AccountService accountService;

	ObjectMapper ObjectMapper = new ObjectMapper();
	Random random = new Random();
	
	@GetMapping
	public ResponseEntity<List<Account>> page() {
		return ResponseEntity.ok(aDAO.findAll());
		
	}

	@GetMapping("/getAuth")
	public Account getAccountAuth() {
		return accountService.getAccountAuth();
	}

	@PostMapping()
	public void post(@RequestBody Account account) {
		accountService.add(account);
	}

	@PutMapping("/{username}")
	public ResponseEntity<Account> put(@RequestBody Account account) {
		return ResponseEntity.ok(accountService.update(account));
	}

	@DeleteMapping("/{username}")
	public void delete(@PathVariable("username") String username) {
		accountService.delete(username);
	}

	// @GetMapping("/randomname")
	// public NameDTO randomName() {
	// 	List<NameDTO> nameDTOs = new ObjectMapper.readValue(null, null);
	// }
}