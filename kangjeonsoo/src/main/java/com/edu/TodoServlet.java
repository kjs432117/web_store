package com.edu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/TodoServlet")
public class TodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TodoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		
		JSONObject obj = new JSONObject();
		
		TodoDAO dao = new TodoDAO();
		
		try {
			List<TodoVO> list = dao.getTodoList();
			JSONArray ary = new JSONArray();
			for(TodoVO vo : list) {
				JSONObject ino = new JSONObject();

				ino.put("content", vo.getContent());
				
				ary.add(ino);
			}
		
			// {"retCode": "Success", "retVal": list}
			obj.put("retCode", "Success");
			obj.put("retVal", ary);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//{"retCode": "Fail","retVal": e.getMessage()}
			obj.put("retCode",  "Fail");
			obj.put("retVal",  e.getMessage());
		}

		response.getWriter().print(obj.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		String content = request.getParameter("content");
		
		
		TodoDAO dao = new TodoDAO();
		JSONObject obj = new JSONObject();
		
		if(cmd.equals("insert")) {
		TodoVO vo = new TodoVO();
		vo.setContent(content);;
		
		try {
			
			dao.insertContent(vo);
			JSONObject inobj = new JSONObject();

			inobj.put("content",  vo.getContent());
			
			// {"retCode":"Success", "retVal": {"id": 3, "name": "hong", "content": "java..."}}
			obj.put("retCode", "Success");
			obj.put("retVal", inobj);
			
		} catch (Exception e) {
			e.printStackTrace();
			// {"retCode":"Fail", "retVal": e.getMessage()}
			obj.put("retCode", "Fail");
			obj.put("retVal",  e.getMessage());
		}
		
		response.getWriter().print(obj.toString());
			
		} else if (cmd.equals("delete")) {
			try {
				dao.deleteContent(content);
				System.out.println(content);
				obj.put("retCode",  "Success");
				obj.put("retVal",  content);
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("retCoe", "Fail");
				obj.put("retVal",e.getMessage());
			}
			response.getWriter().print(obj.toString());


		}
	}

}
