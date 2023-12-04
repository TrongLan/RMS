package com.dtl.rms_server.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dtl.rms_server.dtos.applyinfo.ApplyInfoCreateDTO;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoDashBoard;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;

public interface ApplicantInformationService {

	ApplicantInformation jobApply(ApplyInfoCreateDTO dto) throws RmsException;
	void updateApplyInfosStatus(List<String> uuids, int status)
			throws RmsException;
	ApplicantInformation getDetails(String id) throws RmsException;
	Page<ApplicantInformation> getListApplyInfo(String newsId, int pageNumber)
			throws RmsException;
	ApplyInfoDashBoard getApplyInfoDashboardForNews(String newsId)
			throws RmsException;
}
