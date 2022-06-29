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
//		Connection connection = getConnection();// tạo kết nối
//		PreparedStatement statement = null; 
//		ResultSet resultSet = null;  // tạo resetset lưu kết quả dạng resultSet
//
//		if (connection != null) {
//			try {
//				statement = connection.prepareStatement(sql);// truyền câu sql vào statement
//				statement.setLong(1, categoryId);//truyen gia tri vào cho nay de co the loại bài viết
//		cách hoạt động câu trên là khi truyền thằng id trong findByCategoryId(long categoryId) thì setLong sẽ biến categoryid ấy về kiểu bigint trong Db.
// rồi truyền categoryid đó vào lệnh sql trên
/// 			resultSet = statement.executeQuery();
//				while(resultSet.next()) {
//					NewModel newModel = new NewModel();
//					newModel.setId(resultSet.getLong("id"));
//					newModel.setTitle(resultSet.getString("title"));
//					results.add(newModel);
		// tức là lấy ra filed "id" kiểu long trong resultSet bỏ vào newModel
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

		ResultSet resultSet = null;// dùng cái này để lấy id bài viết id chứ k phải categoryid
		Long id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String sql = "INSERT INTO news (title, content, categoryid) VALUES(?,?,?)";
			connection = getConnection();// mở kết nối
//////nếu khai 	Connection connection = getConnection(); thì thằng connection trong catch không dc đinh nghĩa
////// vì nó chỉ đc định nghĩa trong khối try, vì vậy nên mới khai thành Connection connection = null; như trên///
			connection.setAutoCommit(false);// lệnh này để nó k tự động commit. nếu không false thì lỗi nó có thể vẫn dc
											// lưu 54p40
			statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);// https://www.youtube.com/watch?v=ZN_Dszk7Czk&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=7
			// phút 52
			// truyen cau sql vao statement
			statement.setString(1, newModel.getTitle());
			statement.setString(2, newModel.getContent());
			statement.setLong(3, newModel.getCategoryId());

			/*
			 * đang nói đến 3 dòng trên tức: Đặt tham số được chỉ định thành giá trị Chuỗi
			 * Java đã cho. Trình điều khiển chuyển đổi theo giá trị SQL VARCHAR hoặc
			 * LONGVARCHAR (tùy thuộc vào kích thước đối số liên quan đến giới hạn của trình
			 * điều khiển về giá trị VARCHAR ) khi nó gửi đến cơ sở dữ liệu. ý là chuyển
			 * thằng newModel.getTitle về dạng VARCHAR hoặc LONGVARCHAR khi gửi nó đến
			 * database
			 */

			statement.executeUpdate();// lấy dữ liệu dùng executequey(), còn thao tác dữ liệu như thêm sửa xoá thì
										// dùng executeUpdate
//statement.executeUpdate() thực thi câu lệnh sql và lưu db xuống.
			// khi thực thi câu trên vì id trong db tự tăng nên là vẫn có id
			resultSet = statement.getGeneratedKeys();// vì id tự động tăng tức random nên ta dùng hàm này, để id trong
														// DB
			// sinh ra bao nhiêu thì lấy bấy nhiêu.
			// https://www.youtube.com/watch?v=ZN_Dszk7Czk&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=7
			// 40:05
			if (resultSet.next()) {
				// resultSet.getLong(1);
				id = resultSet.getLong(1);// lấy ra id trong database tức resultSet.getLong(1) rồi gán vào biến id
			} // nói chung trả về id của bài viết đó " trong db id tự động tăng nên dùng
				// getGeneratedKeys();
			connection.commit();// commit thì mới chèn vào database
			// nếu success hết thì commit còn nêu fail ở đâu thì rollback. chỉ khi commit
			// thì database mới change
			return id;
			// return null;
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();// nếu lỗi một cái thì rollback hết mọi thứ
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
				} // làm xong nhớ luôn đóng lại
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
					conn.rollback();// nếu lỗi một cái thì rollback hết mọi thứ
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
