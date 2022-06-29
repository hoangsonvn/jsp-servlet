package com.laptringjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.CategoryModel;

public interface ICategoryDao extends GenericDao<CategoryModel>{
     List<CategoryModel> findAll();
     CategoryModel findOne(long id);

      CategoryModel findOneByCode(String code) ;


}
