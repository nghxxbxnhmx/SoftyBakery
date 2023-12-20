package com.poly.restcontrollers;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.poly.utils.PasswordUtil;
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

	@GetMapping("/{username}")
	public Account account_user(@PathVariable("username") String username) {
		return aDAO.findById(username).orElse(null);
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
	public Account put(@RequestBody Account account) {
		if (aDAO.findById(account.getUsername()).get().getPassword().equals(account.getPassword())) {
			return accountService.update(account);
		} else {
			account.setPassword(PasswordUtil.encode(account.getPassword()));
			return accountService.update(account);
		}
	}

	@DeleteMapping("/{username}")
	public void delete(@PathVariable("username") String username) {
		accountService.delete(username);
	}

	// @GetMapping("/randomname")
	// public NameDTO randomName() {
	// List<NameDTO> nameDTOs = new ObjectMapper.readValue(null, null);
	// }
}