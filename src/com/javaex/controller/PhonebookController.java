package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {

	public PhonebookController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("pbook2Controller");
		// 컨트롤러 (서블릿) 작동 확인

		String act = request.getParameter("action");
		System.out.println(act);

		if ("list".equals(act)) {
			System.out.println("action=list");

			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getPersonList();

			request.setAttribute("pList", personList);

			// forward
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			rd.forward(request, response);
		} else {
			System.out.println("action=null");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
