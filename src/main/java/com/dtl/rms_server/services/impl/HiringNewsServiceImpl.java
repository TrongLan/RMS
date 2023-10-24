package com.dtl.rms_server.services.impl;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.repositories.HiringNewsRepository;
import com.dtl.rms_server.services.HiringNewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HiringNewsServiceImpl implements HiringNewsService {
	private final HiringNewsRepository hiringNewsRepository;

	@Override
	public void uploadHiringNews(@Valid HiringNewsCreateDTO dto)
			throws RmsException {
		
		log.info("Saved successfully.");
		throw new RmsException("Exception throw");
	}

}
