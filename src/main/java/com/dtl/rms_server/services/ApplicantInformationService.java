package com.dtl.rms_server.services;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;

import jakarta.validation.Valid;

public interface ApplicantInformationService {

	ApplicantInformation jobApply(ApplyInfoCreateDTO dto) throws RmsException;
}
