package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptringjavaweb.dao.ICategoryDao;
import com.laptrinhjavaweb.mapper.CategoryMapper;
import com.laptrinhjavaweb.model.CategoryModel;

public class CategoryDao extends AbstracDao<CategoryModel> implements ICategoryDao {

//	public Connection getConnection() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/newservlet12month2018";
//			String user = "root";
//			String password = "1234";
//			return DriverManager.getConnection(url, user, password);
//		} catch (ClassNotFoundException | SQLException e) {
//			return null;
//
//		}
//	}

	@Override
	public List<CategoryModel> findAll() {
		String sql = "SELECT * FROM category";
		return query(sql, new CategoryMapper());
//		return query(sql, new CategoryMapper(), null);

		
//		List<CategoryModel> results = new ArrayList<>();
//		String sql = "SELECT * FROM category";
//		Connection connection = getConnection();
//		PreparedStatement statement = null;
//		ResultSet resultSet = null;
//		if (connection != null) {
//			try {
//				statement = connection.prepareStatement(sql);// khi chay den day moi tao doi tuongw preparedstatement.
//				// de excute ra chung ta can lay doi tuong stament chay 1 cai ham de excutequery
//				// ra, chinh la dong duoi
//				resultSet = statement.executeQuery();
//				while (resultSet.next()) {// lenh nay tuc la nó sẽ chạy kiểm tra đối tượng khác null tức có giá trị thì
//											// vô resultset
//					// get data lên ( tức lấy ra dữ liệu null thi bỏ qua)
//					CategoryModel category = new CategoryModel();
//					category.setId(resultSet.getLong("id"));
//					category.setCode(resultSet.getString("code"));
//					category.setName(resultSet.getString("name"));
//
//					results.add(category);
//				}
//
//				return results;
//			} catch (SQLException e) {
//				return null;
//			} finally {
//				try {
//					if (connection != null) {
//						connection.close();
//					}
//					if (statement != null) {
//						statement.close();
//					}
//					if (resultSet != null) {
//						resultSet.close();
//					}
//				} catch (SQLException e) {
//					return null;
//				}
//
//			}
//		}
//		return null;
	}

	@Override
	public CategoryModel findOne(long id) {
		String sql="SELECT * FROM category WHERE id= ? ";
List<CategoryModel> categoryModel = query(sql,new CategoryMapper(),id);
return categoryModel.isEmpty()?null:categoryModel.get(0);
	}

	@Override
	public CategoryModel findOneByCode(String code) {
		String sql = " SELECT * FROM category WHERE code = ? ";

		List<CategoryModel> categoryModel = query(sql,new CategoryMapper(),code);
		return categoryModel.isEmpty()?null:categoryModel.get(0);
	}
}
