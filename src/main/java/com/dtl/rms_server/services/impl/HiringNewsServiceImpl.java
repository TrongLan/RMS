package com.dtl.rms_server.services.impl;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.CustomRMSMessage;
import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.Account;
import com.dtl.rms_server.models.Category;
import com.dtl.rms_server.models.HiringNews;
import com.dtl.rms_server.repositories.AccountRepository;
import com.dtl.rms_server.repositories.CategoryRepository;
import com.dtl.rms_server.repositories.HiringNewsRepository;
import com.dtl.rms_server.security.UserPrincipal;
import com.dtl.rms_server.services.HiringNewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HiringNewsServiceImpl implements HiringNewsService {
	private final HiringNewsRepository hiringNewsRepository;
	private final AccountRepository accountRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public void uploadHiringNews(@Valid HiringNewsCreateDTO dto)
			throws RmsException {
		HiringNews hiringNews = dto.toHiringNews();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
		var userEmail = principal.getUsername();
		Optional<Account> accountOptional = accountRepository
				.findByEmailIgnoreCaseAndIsActive(userEmail, 1);
		if (accountOptional.isEmpty()) {
			throw new RmsException(
					CustomRMSMessage.ACCOUNT_NOT_EXIST.getContent());
		}
		Account account = accountOptional.get();
		Optional<Category> categoryOptional = categoryRepository
				.findById(dto.getCategoryId());
		if (categoryOptional.isEmpty()) {
			throw new RmsException(
					CustomRMSMessage.CATEGORY_NOT_EXIST.getContent());
		}
		Category category = categoryOptional.get();
		if (category.getIsActive() == 0) {
			throw new RmsException(
					CustomRMSMessage.CATEGORY_NOT_EXIST.getContent());
		}

		hiringNews.setAccount(account);
		hiringNews.setCategory(category);
		hiringNewsRepository.save(hiringNews);
		log.info("Hiring news is created by account id {} ", account.getId());
	}

}
