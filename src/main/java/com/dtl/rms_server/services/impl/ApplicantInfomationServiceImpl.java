package com.dtl.rms_server.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.BasicInfo;
import com.dtl.rms_server.constants.CustomRMSMessage;
import com.dtl.rms_server.constants.HiringNewsStatus;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;
import com.dtl.rms_server.models.HiringNews;
import com.dtl.rms_server.repositories.ApplicantInformationRepository;
import com.dtl.rms_server.repositories.HiringNewsRepository;
import com.dtl.rms_server.services.ApplicantInformationService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicantInfomationServiceImpl
		implements
			ApplicantInformationService {

	private final ApplicantInformationRepository applicantInformationRepository;
	private final HiringNewsRepository hiringNewsRepository;

	@Override
	public ApplicantInformation jobApply(ApplyInfoCreateDTO dto) throws RmsException {
		validate(dto);
		Optional<HiringNews> newsOptional = hiringNewsRepository
				.findById(UUID.fromString(dto.getNewsId()));
		if (newsOptional.isEmpty()) {
			throw new RmsException(
					CustomRMSMessage.HIRING_NEWS_NOT_EXIST.getContent());
		}
		HiringNews hiringNews = newsOptional.get();
		if (hiringNews.getIsActive() == HiringNewsStatus.LOCKED.getValue()) {
			throw new RmsException(
					CustomRMSMessage.HIRING_NEWS_EXPIRED.getContent());
		}
		ApplicantInformation applicantInformation = dto
				.toApplicantInformation();
		applicantInformation.setHiringNews(hiringNews);
		applicantInformation.setApplyDate(LocalDateTime.now());
		return applicantInformationRepository.save(applicantInformation);
	}

	private void validate(ApplyInfoCreateDTO dto) {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<ApplyInfoCreateDTO>> constraintViolationSet = validator
				.validate(dto, BasicInfo.class);
		if (!constraintViolationSet.isEmpty()) {
			String message = constraintViolationSet.stream().findFirst().get()
					.getMessage();
			throw new RmsException(message);
		}
	}

}
