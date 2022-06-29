package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.laptringjavaweb.dao.GenericDao;
import com.laptrinhjavaweb.mapper.RowMapper;

public class AbstracDao<T> implements GenericDao<T> {

//				Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/newservlet12month2018";
//			String user = "root";
//			String password = "1234";
// dung ResourceBundle de config , de tim hon
ResourceBundle resourceBundle = ResourceBundle.getBundle("db");


	public Connection getConnection() {
		try {
			Class.forName(resourceBundle.getString("driverName"));
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
//set parameter()
			setParamter(statement, parameters);// viết sao cho nó ra dạng statement.getLong đó
			// truyen thang statement vi co thang statement
			resultSet = statement.executeQuery();// thực thi câu lệnh sql
			while (resultSet.next()) {

				results.add(rowMapper.mapRow(resultSet));

			}
			return results;
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}

		}

	}

	private void setParamter(PreparedStatement statement, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					statement.setLong(index, (Long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) parameter);
				} else if (parameter == null) {
					statement.setNull(index, Types.NULL);
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	@Override
	public void update(String sql, Object... parameters) {
		ResultSet resultSet = null;// dùng cái này để lấy id bài viết id chứ k phải categoryid
		Long id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			setParamter(statement, parameters);
			statement.executeUpdate();

			int a = 6;
			connection.commit();
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	@Override
	public Long insert(String sql, Object... parameters) {
		ResultSet resultSet = null;// dùng cái này để lấy id bài viết id chứ k phải categoryid
		Long id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			setParamter(statement, parameters);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return null;
			}
		}

	}

	@Override
	public int count(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			int count = 0;
			connection = getConnection();
			statement = connection.prepareStatement(sql);
//set parameter()
			setParamter(statement, parameters);// viết sao cho nó ra dạng statement.getLong đó
			// truyen thang statement vi co thang statement
			resultSet = statement.executeQuery();// thực thi câu lệnh sql
			while (resultSet.next()) {

				count = resultSet.getInt(1); // tý thử để1 thay bằng "count(*)"
			}
			return count;
		} catch (SQLException e) {
			return 0;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				return 0;
			}

		}
	}

}
