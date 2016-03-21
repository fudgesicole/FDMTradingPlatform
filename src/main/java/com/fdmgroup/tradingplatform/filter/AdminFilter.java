package com.fdmgroup.tradingplatform.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Servlet Filter implementation class AdminFilter
 */
//@WebFilter({"/addUser", "/deleteUser", "/syslogs", "/updateUser", "/users", "/userlogs", "/userlist.jsp"})
public class AdminFilter implements Filter {
	private List<String> blockedUrls = new ArrayList<String>();

    /**
     * Default constructor. 
     */
    public AdminFilter() {}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession(false);
		boolean isAdmin = false;
		if (session != null) {
			User loggedInUser = (User) session.getAttribute("user");
			if (loggedInUser != null) {
				for (Role role : loggedInUser.getRoles()) {
					if (role.getName().equals("admin"))
						isAdmin = true;
				}
			}
		}
		if (!isAdmin){
			httpRes.setStatus(HttpServletResponse.SC_FORBIDDEN);
			RequestDispatcher rd = httpReq.getRequestDispatcher("403page.jsp");
			rd.forward(request, response);
		}
		else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
