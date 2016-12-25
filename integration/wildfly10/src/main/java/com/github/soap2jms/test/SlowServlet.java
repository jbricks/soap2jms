package com.github.soap2jms.test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlowServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(SlowServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -7552592324501939896L;

	public SlowServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String queryString = request.getQueryString();
		LOGGER.debug("Call received " + request.getPathInfo() + ",query string [" + queryString + "]");
		String sb = request.getPathInfo();
		if (StringUtils.isBlank(queryString) || !"wsdl".equalsIgnoreCase(queryString)) {
			LOGGER.debug("Call slow down " + request.getPathInfo());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {

			}
		}
		RequestDispatcher rdisp = request.getServletContext().getRequestDispatcher(sb);
		rdisp.forward(request, response);

	}

}
