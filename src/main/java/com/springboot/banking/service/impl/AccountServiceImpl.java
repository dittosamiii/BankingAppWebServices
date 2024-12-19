package com.springboot.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.entity.Account;
import com.springboot.banking.exception.AccountException;
import com.springboot.banking.repository.AccountRepository;
import com.springboot.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
	private ModelMapper modelMapper;

	public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
		super();
		this.accountRepository = accountRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public AccountDto addAccount(AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		return modelMapper.map(accountRepository.save(account), AccountDto.class);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		return modelMapper.map(account, AccountDto.class);
	}

	@Override
	public AccountDto deposit(Long id, Double amount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		double total = account.getBalance() + amount;
		account.setBalance(total);
		return modelMapper.map(accountRepository.save(account), AccountDto.class);
	}

	@Override
	public AccountDto withdraw(Long id, Double amount) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountException("Account does not exists"));
		if (account.getBalance() < amount) {
			throw new RuntimeException("Insufficient Balance");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		return modelMapper.map(accountRepository.save(account), AccountDto.class);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account) -> modelMapper.map(account, AccountDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAccountById(Long id) {
		accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exists"));
		accountRepository.deleteById(id);
	}

}
