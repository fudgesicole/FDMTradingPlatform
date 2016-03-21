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
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginCheck
 */
//@WebFilter("/*")
public class AuthorizationFilter implements Filter {

	private List<String> excludeUrls = new ArrayList<String>();
	private List<String> excludeFolders = new ArrayList<String>();
	
    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
    	excludeUrls.add("/login.jsp");
    	excludeUrls.add("/register.jsp");
    	excludeUrls.add("/index.jsp");
    	excludeUrls.add("/login");
    	excludeUrls.add("/logout");
    	
    	excludeFolders.add("/css/");
    	excludeFolders.add("/fonts/");
    	excludeFolders.add("/img/");
    	excludeFolders.add("/index_files/");
    	excludeFolders.add("/js/");
    	excludeFolders.add("/plugins/");
    }

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
		HttpSession session = httpReq.getSession(false);
		boolean include = false;
		for(String folder : excludeFolders){
			if(httpReq.getServletPath().startsWith(folder))
				include = true;
		}
		if(session == null || (session.getAttribute("user") == null) && !excludeUrls.contains(httpReq.getServletPath()) && !include){
			RequestDispatcher rd = httpReq.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		else{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
