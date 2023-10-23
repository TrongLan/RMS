package com.dtl.rms_server.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dtl.rms_server.models.Account;
import com.dtl.rms_server.services.AccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private final AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountService.findByEmail(username).orElseThrow();
		return UserPrincipal.builder().accountId(account.getId())
				.email(account.getEmail()).password(account.getPassword())
				.build();
	}

}
