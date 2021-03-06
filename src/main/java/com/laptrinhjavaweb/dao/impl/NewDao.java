package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.laptringjavaweb.dao.INewDao;
import com.laptrinhjavaweb.mapper.CategoryMapper;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
import org.apache.commons.lang.StringUtils;

public class NewDao extends AbstracDao<NewModel> implements INewDao {
//	public Connection getConnection() {
//		try {
//
//			Class.forName("com.mysql.jdbc.Driver");
//			String url = "jdbc:mysql://localhost:3306/newservlet12month2018";
//			String user = "root";
//			String password = "1234";
//			return DriverManager.getConnection(url, user, password);
//
//		} catch (ClassNotFoundException | SQLException e) {
//
//			return null;
//		}
//
//	}
//	@Override
//	public List<NewModel> findAll(Integer offset, Integer limit, String sortName, String sortBy) {
//		// String sql = " SELECT * FROM news LIMIT ?,?";
//		StringBuilder sql = new StringBuilder("SELECT * FROM news ");
//		if (sortName != null && sortBy != null) {
//			sql.append(" ORDER BY " + sortName + " " + sortBy + "");
//		}
//		if (offset != null && limit != null) {
//			sql.append(" LIMIT " + offset + "," + limit + "");
//
//		}
//		return query(sql.toString(), new NewMapper());
///// bai cuoi phan trang.12
//	}
	@Override
	public List<NewModel> findAll(Pageble pageble) {
		StringBuilder sql = new StringBuilder("SELECT * FROM news ");
		if (pageble.getSorted() != null && StringUtils.isNotBlank(pageble.getSorted().getSortName())&&StringUtils.isNotBlank(pageble.getSorted().getSortBy())) {
			sql.append(" ORDER BY " + pageble.getSorted().getSortName() + " " + pageble.getSorted().getSortBy() + "");
		}
		if (pageble.getOffset() != null && pageble.getLimit() != null) {
			sql.append(" LIMIT " + pageble.getOffset() + "," + pageble.getLimit() + "");

		}
		return query(sql.toString(), new NewMapper());
	}

	@Override
	public List<NewModel> findByCategoryId(long categoryId) {
		String sql = " SELECT * FROM news WHERE categoryid= ?";
		return query(sql, new NewMapper(), categoryId);

//		List<NewModel> results = new ArrayList<>();
//		String sql = " SELECT * FROM news WHERE categoryid= ?";
//		Connection connection = getConnection();// t???o k???t n???i
//		PreparedStatement statement = null; 
//		ResultSet resultSet = null;  // t???o resetset l??u k???t qu??? d???ng resultSet
//
//		if (connection != null) {
//			try {
//				statement = connection.prepareStatement(sql);// truy???n c??u sql v??o statement
//				statement.setLong(1, categoryId);//truyen gia tri v??o cho nay de co the lo???i b??i vi???t
//		c??ch ho???t ?????ng c??u tr??n l?? khi truy???n th???ng id trong findByCategoryId(long categoryId) th?? setLong s??? bi???n categoryid ???y v??? ki???u bigint trong Db.
// r???i truy???n categoryid ???? v??o l???nh sql tr??n
/// 			resultSet = statement.executeQuery();
//				while(resultSet.next()) {
//					NewModel newModel = new NewModel();
//					newModel.setId(resultSet.getLong("id"));
//					newModel.setTitle(resultSet.getString("title"));
//					results.add(newModel);
		// t???c l?? l???y ra filed "id" ki???u long trong resultSet b??? v??o newModel
//				}
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
//		
//		return null;
	}

	@Override
	public Long save(NewModel newModel) {

		ResultSet resultSet = null;// d??ng c??i n??y ????? l???y id b??i vi???t id ch??? k ph???i categoryid
		Long id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String sql = "INSERT INTO news (title, content, categoryid) VALUES(?,?,?)";
			connection = getConnection();// m??? k???t n???i
//////n???u khai 	Connection connection = getConnection(); th?? th???ng connection trong catch kh??ng dc ??inh ngh??a
////// v?? n?? ch??? ??c ?????nh ngh??a trong kh???i try, v?? v???y n??n m???i khai th??nh Connection connection = null; nh?? tr??n///
			connection.setAutoCommit(false);// l???nh n??y ????? n?? k t??? ?????ng commit. n???u kh??ng false th?? l???i n?? c?? th??? v???n dc
											// l??u 54p40
			statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);// https://www.youtube.com/watch?v=ZN_Dszk7Czk&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=7
			// ph??t 52
			// truyen cau sql vao statement
			statement.setString(1, newModel.getTitle());
			statement.setString(2, newModel.getContent());
			statement.setLong(3, newModel.getCategoryId());

			/*
			 * ??ang n??i ?????n 3 d??ng tr??n t???c: ?????t tham s??? ???????c ch??? ?????nh th??nh gi?? tr??? Chu???i
			 * Java ???? cho. Tr??nh ??i???u khi???n chuy???n ?????i theo gi?? tr??? SQL VARCHAR ho???c
			 * LONGVARCHAR (t??y thu???c v??o k??ch th?????c ?????i s??? li??n quan ?????n gi???i h???n c???a tr??nh
			 * ??i???u khi???n v??? gi?? tr??? VARCHAR ) khi n?? g???i ?????n c?? s??? d??? li???u. ?? l?? chuy???n
			 * th???ng newModel.getTitle v??? d???ng VARCHAR ho???c LONGVARCHAR khi g???i n?? ?????n
			 * database
			 */

			statement.executeUpdate();// l???y d??? li???u d??ng executequey(), c??n thao t??c d??? li???u nh?? th??m s???a xo?? th??
										// d??ng executeUpdate
//statement.executeUpdate() th???c thi c??u l???nh sql v?? l??u db xu???ng.
			// khi th???c thi c??u tr??n v?? id trong db t??? t??ng n??n l?? v???n c?? id
			resultSet = statement.getGeneratedKeys();// v?? id t??? ?????ng t??ng t???c random n??n ta d??ng h??m n??y, ????? id trong
														// DB
			// sinh ra bao nhi??u th?? l???y b???y nhi??u.
			// https://www.youtube.com/watch?v=ZN_Dszk7Czk&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=7
			// 40:05
			if (resultSet.next()) {
				// resultSet.getLong(1);
				id = resultSet.getLong(1);// l???y ra id trong database t???c resultSet.getLong(1) r???i g??n v??o bi???n id
			} // n??i chung tr??? v??? id c???a b??i vi???t ???? " trong db id t??? ?????ng t??ng n??n d??ng
				// getGeneratedKeys();
			connection.commit();// commit th?? m???i ch??n v??o database
			// n???u success h???t th?? commit c??n n??u fail ??? ????u th?? rollback. ch??? khi commit
			// th?? database m???i change
			return id;
			// return null;
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();// n???u l???i m???t c??i th?? rollback h???t m???i th???
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
				} // l??m xong nh??? lu??n ????ng l???i
			} catch (SQLException e) {
				return null;
			}
		}

	}

	@Override
	public Long suaDemo(long id) {
		String sql = " UPDATE news SET title = 'bai viet demo i1' WHERE id = ?";

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
			conn.commit();
			return id;

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();// n???u l???i m???t c??i th?? rollback h???t m???i th???
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return null;
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	}

	@Override
	public void update(NewModel updateNew) {
		StringBuilder sql = new StringBuilder("UPDATE news SET title= ?, thumbnail=?,");
		sql.append("shortdescription = ?, content = ?, categoryid=?,");
		sql.append("createddate=?,createdby=?,modifiedby=?,modifieddate=? WHERE id=?");
		// String sql = "UPDATE news SET title= ?, content= ?, categoryid= ? WHERE id=
		// ?";
		update(sql.toString(), updateNew.getTitle(), updateNew.getThumbnail(), updateNew.getShortDescription(),
				updateNew.getContent(), updateNew.getCategoryId(), updateNew.getCreatedDate(), updateNew.getCreatedBy(),
				updateNew.getModifiedBy(),updateNew.getModifiedDate(),updateNew.getId());
	}

	@Override
	public Long saveInsert(NewModel newModel) {
		String sql = "INSERT INTO news (title,thumbnail,shortdescription,content,categoryid,createddate,createdby,modifieddate,modifiedby) VALUES (?,?,?,?,?,?,?,?,?)";
		return insert(sql, newModel.getTitle(), newModel.getThumbnail(), newModel.getShortDescription(),
				newModel.getContent(), newModel.getCategoryId(), newModel.getCreatedDate(), newModel.getCreatedBy(),
				newModel.getModifiedDate(), newModel.getModifiedBy());
	}

	@Override
	public NewModel findOne(Long id) {
		String sql = "SELECT * FROM news WHERE id = ?";
		List<NewModel> news = query(sql, new NewMapper(), id);
		return news.isEmpty() ? null : news.get(0);
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM news WHERE id=?";
		update(sql, id);
	}

	@Override
	public int getTotalItem() {
		String sql = "SELECT count(*) FROM news";
		return count(sql);
	}

}
