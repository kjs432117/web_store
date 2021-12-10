package com.edu;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends DAO {
	// 전체조회.
	public List<TodoVO> getTodoList() throws Exception {
		List<TodoVO> list = new ArrayList<>();

		String sql = "select * from todo_list order by 1";

		connect();

		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			TodoVO vo = new TodoVO();
			vo.setContent(rs.getString("content"));

			list.add(vo);

		}

		disconnect();

		return list;

	}

	// 한건입력.
	public TodoVO insertContent(TodoVO vo) throws Exception {
		String sql = "insert into todo_list values(?)";
	

		connect();

		psmt = conn.prepareStatement(sql);
		psmt.setString(1, vo.getContent());

		int r = psmt.executeUpdate();
		System.out.println(r + "건 입력.");

		disconnect();
		
		return vo;
	}

	

	// 삭제.
	public String deleteContent(String content) throws Exception {
		String sql = "delete from todo_list where content=?";

		connect();
		psmt = conn.prepareStatement(sql);
		psmt.setString(1, content);

		int r = psmt.executeUpdate();

		System.out.println(r + "건 삭제.");
		disconnect();

		return content;

	}

}
