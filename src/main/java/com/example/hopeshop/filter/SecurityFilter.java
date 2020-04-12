package com.example.hopeshop.filter;


import com.example.hopeshop.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns="/admin/*")
public class SecurityFilter implements Filter {

	@Override
	public void destroy() {


	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resq= (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Object obj=session.getAttribute("account");
		User user=(User) obj;

		if(user !=null && user.getRoleId()==1 ) {
			chain.doFilter(request, response);
			return; //
		}else {
			resq.sendRedirect(req.getContextPath()+ "/logout");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {


	}


}
