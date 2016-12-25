package com.github.soap2jms.test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class Servlet500 extends HttpServlet {

	private static final long serialVersionUID = -7552592324501939896L;

	public Servlet500() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sb = request.getPathInfo();
		if (StringUtils.isBlank(request.getQueryString()) || !"wsdl".equals(request.getQueryString())) {
			response.sendError(500, "<H1>Internal server error</H1>");
			return;
		}
		RequestDispatcher rdisp = request.getServletContext().getRequestDispatcher(sb);
		rdisp.forward(request, response);
	}

}
