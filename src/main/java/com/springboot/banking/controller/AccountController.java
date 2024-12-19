package com.springboot.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
		return new ResponseEntity<>(accountService.addAccount(accountDto), HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getAccountById(id));
	}

	@PutMapping("{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
		double amount = request.get("balance");
		return ResponseEntity.ok(accountService.deposit(id, amount));
	}

	@PutMapping("{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
		double amount = request.get("balance");
		return ResponseEntity.ok(accountService.withdraw(id, amount));
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts() {
		return ResponseEntity.ok(accountService.getAllAccounts());
	}

	@DeleteMapping("{id}/delete")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccountById(id);
		return ResponseEntity.ok("Account deleted successfully!");
	}
}
