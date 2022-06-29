package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptringjavaweb.dao.ICategoryDao;
import com.laptringjavaweb.dao.INewDao;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.INewService;

public class NewService implements INewService {

    @Inject
    private INewDao newDao;
    @Inject
    private ICategoryDao categoryDao;

    @Override
    public List<NewModel> findByCategoryId(long categoryId) {
        // TODO Auto-generated method stub
        return newDao.findByCategoryId(categoryId);
    }

//	@Override
//	public List<NewModel> findAll(Integer offset, Integer limit, String sortName, String sortBy) {
//		// TODO Auto-generated method stub
//		return newDao.findAll(offset, limit,sortName,sortBy);
//	}

    @Override
    public List<NewModel> findAll(Pageble pageble) {
        // TODO Auto-generated method stub
        return newDao.findAll(pageble);
    }

    @Override
    public NewModel findOne(long id) {
        NewModel newModel = newDao.findOne(id);
        CategoryModel categoryModel = categoryDao.findOne(newModel.getCategoryId());
        newModel.setCategoryCode(categoryModel.getCode());

        return newModel;
    }

    @Override
    public NewModel update(NewModel updateNew) {
        // TODO Auto-generated method stub
//		Long newId = newDao.save(newModel);
//		System.out.println(newId);
//		return null;

//	 newDao.update(newModel);
//	 Long id = newModel.getId();
//	 
//		return newDao.findOne(id);
        NewModel oldNew = newDao.findOne(updateNew.getId());// chay xong khong co thumbail va shortdescription
        updateNew.setCreatedDate(oldNew.getCreatedDate());
        try {
            updateNew.setCreatedBy(oldNew.getCreatedBy());// chuyen createdate,by ve oldnew vi may cai nay du nguyen
        } catch (NullPointerException e) {

        }
        updateNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        CategoryModel categoryModel = categoryDao.findOneByCode(updateNew.getCategoryCode());// 2 dong nay duoc them vao luc https://www.youtube.com/watch?v=kfOZ3vGHYdM&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=45 3:00
        updateNew.setCategoryId(categoryModel.getId());//
        newDao.update(updateNew);
        return newDao.findOne(updateNew.getId());
    }

    @Override
    public Long suaDemo(long id) {
        return newDao.suaDemo(id);
    }

    @Override
    public NewModel saveInset(NewModel newModel) {// kiểu thêm 1 newmodel rồi hiện nó ra
        // vì thằng insert() thuộc AbtractDao tức thuộc NewDao nên class này vẫn có
        // insert(), tại sao k viết câu lệnh sql ở đây rồi
        // chạy, bởi vì đấy là priciple"khung " , mấy câu dính Sql nên viết tâng Dao.
        newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        newModel.setCategoryId(categoryDao.findOneByCode(newModel.getCategoryCode()).getId());
		Long newId = newDao.saveInsert(newModel);// thêm 1 newmodel

        return newDao.findOne(newId);// hiện ra theo id
    }

    @Override
    public void delete(long[] ids) {
        for (long id : ids) {
            // 1.delete comment(khoá ngoại new_id)
            // 2. delete news

            newDao.delete(id);
        }
    }

    @Override
    public int getTotalItem() {
        // TODO Auto-generated method stub
        return newDao.getTotalItem();
    }


}
