package com.laptringjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.mapper.RowMapper;

// neu truyen categoryModel vo thi T co kieu du lieu categoryModel thi
// dong duoi  nuaawx cung tra ve categoryModel
public interface GenericDao<T> {
	<T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
	void update(String sql, Object... parameters);
	Long insert(String sql, Object... parameters);
	int count(String sql, Object...parameters);
}
