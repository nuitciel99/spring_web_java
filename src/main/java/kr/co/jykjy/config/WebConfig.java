package kr.co.jykjy.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	// throwExceptionIfNoHandlerFound
	@Override
	protected void customizeRegistration(Dynamic registration) {
//		DispatcherServlet dispatcherServlet = new DispatcherServlet();
//		dispatcherServlet.setThrowExceptionIfNoHandlerFound(throwExceptionIfNoHandlerFound);
		
		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");

		MultipartConfigElement element = new MultipartConfigElement(
				"c:/upload", 
				2 * 1024 * 1024, 
				10 * 1024 * 1024, 
				2 * 1024 * 1024);
		registration.setMultipartConfig(element);
	}

//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter filter = new CharacterEncodingFilter("utf-8", true);
//		
//		return new Filter[] {filter};
//	}
	
}
