package com.dtl.rms_server.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.AccountStatus;
import com.dtl.rms_server.models.Account;
import com.dtl.rms_server.repositories.AccountRepository;
import com.dtl.rms_server.services.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;

	@Override
	public Optional<Account> findByEmail(String email) {
		return accountRepository.findByEmailIgnoreCaseAndIsActive(email,
				AccountStatus.ACTIVE.getValue());
	}

}
