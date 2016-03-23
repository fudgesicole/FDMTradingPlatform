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
 * Servlet Filter implementation class AuthorizationFilter
 * Filters access to all pages.
 */
@WebFilter("/*")
public class AuthorizationFilter implements Filter {

	//list of URLs that all users including guests can access
	private List<String> excludeUrls = new ArrayList<String>();
	//list of all folders containing resources that all users including guests can access 
	private List<String> excludeFolders = new ArrayList<String>();
	
    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
    	excludeUrls.add("/testLogin");
    	excludeUrls.add("/userExists");
    	excludeUrls.add("/login");
    	excludeUrls.add("/register");
    	excludeUrls.add("/");
    	
    	excludeFolders.add("/resources/");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpSession session = httpReq.getSession(false);
		
		boolean include = false;
		for(String folder : excludeFolders){
			//if the URL the user is attempting to access is within one of the folders
			//guests are allowed to access
			if(httpReq.getServletPath().startsWith(folder))
				include = true;
		}
		if((session == null || (session.getAttribute("loggedInUser") == null)) && !excludeUrls.contains(httpReq.getServletPath()) && !include){
			//if the requested URL is not allowed to guests and there is not logged in user
			//this 'errMsg' attribute is displayed in a modal on page load.
			request.setAttribute("errMsg", "You do not have permission to access this page. If you believe this is an error, please contact an administrator.");
			//redirect to the the index page/ 
			RequestDispatcher rd = httpReq.getRequestDispatcher("/");
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
	}

}
