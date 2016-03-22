package com.fdmgroup.tradingplatform.filter;

import java.io.IOException;

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

import com.fdmgroup.tradingplatform.model.entity.Role;
import com.fdmgroup.tradingplatform.model.entity.User;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter({"/userList", "/adminAddUser", "/adminDeleteUser", "/adminEditUser"})
public class AdminFilter implements Filter {

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
		HttpSession session = httpReq.getSession(false);
		boolean isAdmin = false;
		if (session != null) {
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			if (loggedInUser != null) {
				for (Role role : loggedInUser.getRoles()) {
					if (role.getName().equals("admin"))
						isAdmin = true;
				}
			}
		}
		if (!isAdmin){
			request.setAttribute("errMsg", "You do not have permission to access this page. If you believe this is an error, please contact an administrator.");
			RequestDispatcher rd = httpReq.getRequestDispatcher("/");
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
