package com.laptrinhjavaweb.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.laptringjavaweb.dao.ICategoryDao;
import com.laptrinhjavaweb.dao.impl.CategoryDao;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.service.ICategoryService;

public class CategoryService implements ICategoryService{
//
//private ICategoryDao categoryDao;
//	
//	public CategoryService() {
//		categoryDao = new CategoryDao();
//	}
	
	// làm đến đây trước khi mấy dòng trên dùng dấu xược thi khai bao thu vien <weld.servlet.version>1.1.0.Final</weld.servlet.version>
	// tạo tiếp beans.xml
	@Inject
	private ICategoryDao categoryDao;
	
	@Override
	public List<CategoryModel> findAll() {
		return categoryDao.findAll();
	}

}
