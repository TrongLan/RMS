package com.dtl.rms_server.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dtl.rms_server.models.ApplicantInformation;
import com.dtl.rms_server.models.HiringNews;

public interface ApplicantInformationRepository
		extends
			JpaRepository<ApplicantInformation, UUID> {
	List<ApplicantInformation> findAllByHiringNewsAndStatusIn(
			HiringNews hiringNews, List<Integer> status);

	Page<ApplicantInformation> findAllByHiringNewsAndStatusIn(
			HiringNews hiringNews, List<Integer> status, Pageable pageable);

	List<ApplicantInformation> findAllByHiringNews(HiringNews hiringNews);
}
