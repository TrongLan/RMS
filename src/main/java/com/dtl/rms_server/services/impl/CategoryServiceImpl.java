package com.dtl.rms_server.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dtl.rms_server.constants.Common;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.Category;
import com.dtl.rms_server.repositories.CategoryRepository;
import com.dtl.rms_server.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() throws RmsException {
		return categoryRepository.findAllByIsActive(Common.ACTIVE.getValue());
	}

}
