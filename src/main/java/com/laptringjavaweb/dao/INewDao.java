package com.laptringjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
//build xong thu xoa newmodel trong generic coi sao
//public interface INewDao extends GenericDao<NewModel>{
	public interface INewDao extends GenericDao<NewModel>{

	List<NewModel> findByCategoryId(long categoryid);
//	List<NewModel> findAll(Integer offset, Integer limit,String sortName, String sortBy);
	List<NewModel> findAll(Pageble pageble);
	Long save(NewModel newModel);// dùng hàm này trả về id bài viết
	Long suaDemo(long id);
	void update(NewModel updateNew);//core
	Long saveInsert(NewModel NewModel);//core
	NewModel findOne(Long id);//https://www.youtube.com/watch?v=9zNUO06Gjx8&list=PLW1k06REn7Hv8vlX-m4fiTfTuwpC9HpLm&index=4 10:35
	void delete(long id);
int getTotalItem();

}
