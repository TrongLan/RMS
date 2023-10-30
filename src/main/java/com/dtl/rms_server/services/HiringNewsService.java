package com.dtl.rms_server.services;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;

public interface HiringNewsService {
	String uploadHiringNews(HiringNewsCreateDTO dto) throws RmsException;

}
