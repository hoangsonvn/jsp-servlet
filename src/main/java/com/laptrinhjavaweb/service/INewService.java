package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

import javax.enterprise.inject.New;

public interface INewService {
	List<NewModel> findByCategoryId(long categoryId) ;
	//List<NewModel> findAll(Integer offset, Integer limit, String sortName, String sortBy);
    NewModel update(NewModel updateNew);
    Long suaDemo(long id);
    NewModel saveInset(NewModel newModel);
    void delete(long[] ids);
    int getTotalItem();
    List<NewModel> findAll(Pageble pageble);
    NewModel findOne(long id);
    
}
