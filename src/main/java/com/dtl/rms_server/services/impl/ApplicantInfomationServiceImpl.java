package com.dtl.rms_server.services.impl;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.ApplyInfoStatus;
import com.dtl.rms_server.constants.BasicInfo;
import com.dtl.rms_server.constants.Common;
import com.dtl.rms_server.constants.CustomRMSMessage;
import com.dtl.rms_server.constants.HiringNewsStatus;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoDashBoard;
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
	public ApplicantInformation jobApply(ApplyInfoCreateDTO dto)
			throws RmsException {
		validateDefault(dto);
		UUID hiringNewsID;
		try {
			hiringNewsID = UUID.fromString(dto.getNewsId());
		} catch (IllegalArgumentException e) {
			log.error("This string '{}' is not a valid UUID.", dto.getNewsId());
			throw new RmsException(
					CustomRMSMessage.SOMETHING_WENT_WRONG.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		Optional<HiringNews> newsOptional = hiringNewsRepository
				.findById(hiringNewsID);
		if (newsOptional.isEmpty()) {
			throw new RmsException(
					CustomRMSMessage.HIRING_NEWS_NOT_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		HiringNews hiringNews = newsOptional.get();
		if (hiringNews.getIsActive() == HiringNewsStatus.LOCKED.getValue()) {
			throw new RmsException(
					CustomRMSMessage.HIRING_NEWS_EXPIRED.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		validateCustom(dto, hiringNews);
		ApplicantInformation applicantInformation = dto
				.toApplicantInformation();
		applicantInformation.setHiringNews(hiringNews);
		applicantInformation.setApplyDate(LocalDateTime.now());
		return applicantInformationRepository.save(applicantInformation);
	}

	private void validateDefault(ApplyInfoCreateDTO dto) {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		var constraintViolationSet = validator.validate(dto, BasicInfo.class);
		if (!constraintViolationSet.isEmpty()) {
			ConstraintViolation<ApplyInfoCreateDTO> constraintViolation = constraintViolationSet
					.stream().findFirst().orElse(null);
			String message = Objects.isNull(constraintViolation)
					? ""
					: constraintViolation.getMessage();
			throw new RmsException(message, HttpStatus.BAD_REQUEST);
		}
	}

	private void validateCustom(ApplyInfoCreateDTO dto, HiringNews hiringNews) {
		List<ApplicantInformation> allByHiringNews = applicantInformationRepository
				.findAllByHiringNews(hiringNews);
		List<String> emails = allByHiringNews.stream()
				.map(ApplicantInformation::getEmail).toList();
		List<String> phoneNumbers = allByHiringNews.stream()
				.map(ApplicantInformation::getPhoneNumber).toList();
		if (emails.contains(dto.getEmail())) {
			throw new RmsException(
					CustomRMSMessage.EMAIL_APPLY_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		if (phoneNumbers.contains(dto.getPhoneNumber())) {
			throw new RmsException(
					CustomRMSMessage.PHONE_NUMBER_APPLY_EXIST.getContent(),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public void updateApplyInfosStatus(List<String> uuids, int status)
			throws RmsException {
		// validate
		List<UUID> ids;
		try {
			ids = uuids.stream().map(UUID::fromString).toList();
		} catch (IllegalArgumentException e) {
			log.error("Error in converting string to UUID");
			throw new RmsException(
					CustomRMSMessage.SOMETHING_WENT_WRONG.getContent(),
					HttpStatus.BAD_REQUEST);
		}
		List<ApplicantInformation> applyList = applicantInformationRepository
				.findAllById(ids);
		for (var apply : applyList) {
			apply.setStatus(status);
		}
		applicantInformationRepository.saveAll(applyList);

	}

	@Override
	public ApplicantInformation getDetails(String id) throws RmsException {
		return applicantInformationRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new RmsException(
						CustomRMSMessage.APLLY_INFO_NOT_EXIST.getContent(),
						HttpStatus.BAD_REQUEST));
	}

	@Override
	public Page<ApplicantInformation> getListApplyInfo(String newsId,
			int pageNumber) throws RmsException {
		HiringNews hiringNews = hiringNewsRepository
				.findById(UUID.fromString(newsId))
				.orElseThrow(() -> new RmsException(
						CustomRMSMessage.HIRING_NEWS_NOT_EXIST.getContent(),
						HttpStatus.BAD_REQUEST));
		Pageable pageable = PageRequest.of(pageNumber, 10,
				Sort.by(Order.asc("status"), Order.desc("applyDate")));
		return applicantInformationRepository.findAllByHiringNewsAndStatusIn(
				hiringNews, ApplyInfoStatus.map().keySet().stream().toList(),
				pageable);
	}

	@Override
	public ApplyInfoDashBoard getApplyInfoDashboardForNews(String newsId)
			throws RmsException {
		HiringNews hiringNews = hiringNewsRepository
				.findById(UUID.fromString(newsId))
				.orElseThrow(() -> new RmsException(
						CustomRMSMessage.HIRING_NEWS_NOT_EXIST.getContent(),
						HttpStatus.BAD_REQUEST));
		List<ApplicantInformation> applyInfos = applicantInformationRepository
				.findAllByHiringNews(hiringNews);
		long total = applyInfos.size();
		long suitable = applyInfos.stream().filter(
				t -> t.getStatus() == ApplyInfoStatus.APPROVED.getValue())
				.count();
		long notSuitable = applyInfos.stream()
				.filter(t -> t.getStatus() == ApplyInfoStatus.DENIED.getValue())
				.count();
		long pending = applyInfos.stream()
				.filter(t -> t.getStatus() == ApplyInfoStatus.UNSEEN.getValue())
				.count();
		return ApplyInfoDashBoard.builder().total(total).suitable(suitable)
				.notSuitable(notSuitable).pending(pending).build();
	}

}
