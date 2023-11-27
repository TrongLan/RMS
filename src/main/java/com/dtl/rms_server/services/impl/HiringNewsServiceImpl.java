package com.dtl.rms_server.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.Common;
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
	public String uploadHiringNews(HiringNewsCreateDTO dto)
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
					CustomRMSMessage.ACCOUNT_NOT_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		Account account = accountOptional.get();
		Optional<Category> categoryOptional = categoryRepository
				.findById(dto.getCategoryId());
		if (categoryOptional.isEmpty()) {
			throw new RmsException(
					CustomRMSMessage.CATEGORY_NOT_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		Category category = categoryOptional.get();
		if (category.getIsActive() == 0) {
			throw new RmsException(
					CustomRMSMessage.CATEGORY_NOT_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}

		hiringNews.setAccount(account);
		hiringNews.setCategory(category);
		hiringNews.setIsActive(Common.ACTIVE.getValue());
		log.info("Hiring news is created by account id {} ", account.getId());
		return hiringNewsRepository.save(hiringNews).getId().toString();
	}

	@Override
	public HiringNews getHiringNewsDetails(String id) throws RmsException {
		return hiringNewsRepository
				.findByIdAndIsActive(UUID.fromString(id),
						Common.ACTIVE.getValue())
				.orElseThrow(() -> new RmsException(
						CustomRMSMessage.HIRING_NEWS_NOT_EXIST.getContent(),
						HttpStatus.NOT_FOUND));
	}

	@Override
	public List<HiringNews> getNewsOfCategory(long categoryId)
			throws RmsException {
		Category category = categoryRepository
				.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new RmsException(
						CustomRMSMessage.CATEGORY_NOT_EXIST.getContent(),
						HttpStatus.BAD_REQUEST));
		if (category.getIsActive() == Common.LOCKED.getValue()) {
			throw new RmsException(
					CustomRMSMessage.CATEGORY_LOCKED.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		return hiringNewsRepository
				.findAllByCategoryAndIsActiveOrderByDueDateDesc(category,
						Common.ACTIVE.getValue());
	}

}
