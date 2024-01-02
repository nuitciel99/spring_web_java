package kr.co.jykjy.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	@Override
	public void handle(HttpServletRequest arg0, HttpServletResponse arg1, AccessDeniedException arg2)
			throws IOException, ServletException {

		log.error("Access Denied Handler");
		log.error("redirect");
		
//		arg1.sendRedirect("/accessError");
//		arg0
		arg0.getRequestDispatcher("/accessError").forward(arg0, arg1);
		
	}
	
}
