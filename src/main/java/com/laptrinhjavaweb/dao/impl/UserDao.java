package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import com.laptringjavaweb.dao.IUserDao;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.mapper.UserMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;

public class UserDao extends AbstracDao<UserModel> implements IUserDao{
	/*
	 * StringBuilder s1 = new StringBuilder (“Xin chào”);
	 *	 * s1.append (“Thế giới”);
	 *  System.out.println (s1);
	 * * Trong câu lệnh 1, s1 đang tham chiếu đến đối tượng "Xin chào" trong một đống.
	 * Đối tượng có thể thay đổi vì nó được tạo bằng StringBuilder. Trong câu lệnh
	 * 2, “World” được thêm vào cùng một đối tượng Chuỗi “Hello”. Không có tạo đối
	 * tượng Chuỗi hoàn toàn mới.
	 */
	@Override
	public UserModel findByUserNameAndPasswordAndStatus(String userName, String password, Integer status) {
		StringBuilder sql = new StringBuilder("SELECT * FROM user AS u");
		sql.append(" INNER JOIN role AS r ON r.id = u.roleid");
		sql.append(" WHERE username = ? AND password = ? AND status = ?");
		List<UserModel> users = query(sql.toString(), new UserMapper(), userName, password, status);
		return users.isEmpty() ? null : users.get(0);
	}

}
