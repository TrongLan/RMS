package com.dtl.rms_server.services;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;

public interface HiringNewsService {
	void uploadHiringNews(HiringNewsCreateDTO dto) throws Exception;

}
