package com.dtl.rms_server.services;

import java.util.List;

import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.Category;

public interface CategoryService {
	List<Category> getAllCategories() throws RmsException;
}
