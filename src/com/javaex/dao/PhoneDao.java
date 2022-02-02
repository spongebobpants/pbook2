package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// list
	public List<PersonVo> getPersonList() {
		return getPersonList("");
	}

	public List<PersonVo> getPersonList(String keword) {
		List<PersonVo> personList = new ArrayList<PersonVo>();
		getConnection();

		try {
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person";

			if (keword != "" || keword == null) {
				query += " where name like ? ";
				query += " or hp like  ? ";
				query += " or company like ? ";

				pstmt = conn.prepareStatement(query);

				// binding
				pstmt.setString(1, '%' + keword + '%');
				pstmt.setString(2, '%' + keword + '%');
				pstmt.setString(3, '%' + keword + '%');
			} else {
				pstmt = conn.prepareStatement(query);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error" + e);
		}
		close();
		return personList;
	}

}
