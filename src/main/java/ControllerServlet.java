package com.cci.javademoapp;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

@WebServlet("/demo")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DataService db;   
    public ControllerServlet() {
        super();
		System.out.println("ControllerServlet Begin");
        db = new DataService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet Begin");
		request.setAttribute("current_text", db.getCurrentText());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost Begin");
		String item = request.getParameter("item");
		if(db.updateText(item)) System.out.println("Text Updated");
		doGet(request, response);
	}
}
