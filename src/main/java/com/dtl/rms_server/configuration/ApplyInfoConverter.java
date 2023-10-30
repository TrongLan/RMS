package com.dtl.rms_server.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dtl.rms_server.constants.CustomRMSMessage;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplyInfoConverter
		implements
			Converter<String, ApplyInfoCreateDTO> {

	private final ObjectMapper mapper;

	@Override
	public ApplyInfoCreateDTO convert(String source) {
		try {
			return mapper.readValue(source, ApplyInfoCreateDTO.class);
		} catch (JsonProcessingException e) {
			throw new RmsException(
					CustomRMSMessage.APPLY_INFO_WRONG_DATA_FORMAT.getContent());
		}
	}

}
