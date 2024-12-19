package com.springboot.banking.service;

import java.util.List;

import com.springboot.banking.dto.AccountDto;

public interface AccountService {
	AccountDto addAccount(AccountDto accountDto);

	AccountDto getAccountById(Long id);

	AccountDto deposit(Long id, Double amount);

	AccountDto withdraw(Long id, Double amount);

	List<AccountDto> getAllAccounts();

	void deleteAccountById(Long id);
}
