package org.configManager.interceptor;

import java.io.IOException;




import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class AuthorityFilter implements Filter {

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		  	HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        try {
	        	Object loginUser = request.getSession().getAttribute("loginUser");
	        	String requestURI = request.getRequestURI();
	        	if(requestURI.indexOf("system") > 0 || requestURI.equals("/configManager-web/")){
	        		chain.doFilter(request, response);
	        		return;
	        	}
        		if (loginUser != null) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect("/configManager-web/");
				}
	        } catch(Exception e) {
	        	e.printStackTrace();
	        	response.sendRedirect("/configManager-web/");
	        }
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
