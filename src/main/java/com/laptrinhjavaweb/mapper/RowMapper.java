package com.laptrinhjavaweb.mapper;

import java.sql.ResultSet;

// goi la row vi resultset di vo tung row trong sql roi get gia tri nos ra
//  ma lay tung fileds gia tri cua row nen la mapper
public interface RowMapper<T> {
	T mapRow(ResultSet resultSet);

}
