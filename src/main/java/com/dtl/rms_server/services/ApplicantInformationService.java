package com.dtl.rms_server.services;

import java.util.List;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;

public interface ApplicantInformationService {

	ApplicantInformation jobApply(ApplyInfoCreateDTO dto) throws RmsException;
	void updateApplyInfosStatus(List<String> uuids, int status);
}
