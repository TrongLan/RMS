package com.dtl.rms_server.services;

import java.util.List;

import com.dtl.rms_server.dtos.hiringnews.HiringNewsCreateDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.HiringNews;

public interface HiringNewsService {
	String uploadHiringNews(HiringNewsCreateDTO dto) throws RmsException;
	HiringNews getHiringNewsDetails(String id) throws RmsException;
	List<HiringNews> getNewsOfCategory(long categoryId) throws RmsException;
}
