package ch.bbzbl.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.bbzbl.bean.LoginBean;
import ch.bbzbl.entity.User;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		if(session.isNew()) {
			
			login(request, resp, req);
			return;
		}
		
		User user = (User) session.getAttribute(LoginBean.ATTR_USER);
		
		if(user == null) {
			
			login(request, resp, req);
			return;
		}
				    
		chain.doFilter(request, resp);
		
	}

	private void login(ServletRequest request, ServletResponse resp, HttpServletRequest req) throws ServletException, IOException{
		RequestDispatcher disp = req.getRequestDispatcher("/pages/public/login.xhtml");
		disp.forward(request, resp);
	}
}
