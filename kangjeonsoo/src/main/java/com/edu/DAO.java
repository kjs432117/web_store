package com.edu;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	
	protected Connection conn;
	protected PreparedStatement psmt;
	protected Statement stmt;
	protected ResultSet rs;
	
	protected void connect() {
		
		try {
			//1.JDBC Driver 로딩하기.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2.DB 서버 접속하기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "hr";
			String password = "hr";
			conn = DriverManager.getConnection(url, id, password);
			System.out.println("연결성공");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	protected void disconnect() {
		if(rs != null) {
			try {
				rs.close();	
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();	
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(psmt != null) {
			try {
				psmt.close();	
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt != null) {
			try {
				stmt.close();	
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}
