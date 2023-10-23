package com.dtl.rms_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtl.rms_server.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	Optional<Account> findByEmailIgnoreCaseAndIsActive(String email, int isActive);
}
