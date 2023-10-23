package com.dtl.rms_server.services;

import java.util.Optional;

import com.dtl.rms_server.models.Account;

public interface AccountService {
	Optional<Account> findByEmail(String email);
}
