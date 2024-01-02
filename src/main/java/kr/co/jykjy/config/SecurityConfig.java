package kr.co.jykjy.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.jykjy.security.CustomAccessDeniedHandler;
import kr.co.jykjy.security.CustomLoginSuccessHandler;
import kr.co.jykjy.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 
		/*
		 * 게시글 board insert(get, post) // authenticated 
		 * update(get, post) // authenticated && mine || admin
		 * delete(post) // authenticated && mine || admin
		 * selectList(get) // anonymous
		 * selectOne(get) // anonymous
		 * 
		 * login // anonymous
		 * join // anonymous
		 * mypage // get authenticated
		 * update // get, post fullyAuthenticated
		 * delete // post fullyAuthenticated
		 * 
		 * */
		
		// 한글 
		http.addFilterBefore(new CharacterEncodingFilter("utf-8", true), CsrfFilter.class)
			.formLogin().loginPage("/member/login")
//			.successHandler(new CustomLoginSuccessHandler())
			.and()
				.logout().invalidateHttpSession(true) //.logoutSuccessUrl("/sample/all")
			.and()
				.csrf().ignoringAntMatchers("/uploadAjax","/deleteFile")
			.and()
				.rememberMe().tokenValiditySeconds(604800).tokenRepository(jdbcTokenRepositoryImpl()); // post
		
		// get post put delete
		
//		http.authorizeRequests()
//			.antMatchers("/sample/all").permitAll()
//			.antMatchers("/sample/member")
//			.access("hasRole('ROLE_MEMBER')")
//			.antMatchers("/sample/admin")
//			.access("hasRole('ROLE_ADMIN')");
//		
//		http.csrf().ignoringAntMatchers("/uploadAjax","/deleteFile");
////		http.authorizeRequests().antMatchers("/board/*").authenticated().antMatchers("/board/list").anonymous();
//		
//		http.rememberMe().tokenValiditySeconds(604800).tokenRepository(jdbcTokenRepositoryImpl());
		
		
		// 1. rest 전환
		// 2. csrf 토큰 처리
		
//      	.hasRole("ROLE_MEMBER");
//      	.access("ROLE_MEMBER");
		
//		http.exceptionHandling()
//		.accessDeniedPage("/accessError");
//		.accessDeniedHandler(accessDeniedHandler());
		
	}
	
	@Bean
	public JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl(){
		JdbcTokenRepositoryImpl impl = new JdbcTokenRepositoryImpl();
		impl.setDataSource(dataSource);
		return impl;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
//			.jdbcAuthentication().dataSource(dataSource)
//			.usersByUsernameQuery("select id username, pw password, enabled from tbl_member where id = ?")
//			.authoritiesByUsernameQuery("select id username, auth authority from tbl_auth where id = ?")
//		.and()
			.userDetailsService(userDetailsService());
		
//		auth.inMemoryAuthentication()
//			.withUser("member").password("{noop}1234").roles("MEMBER")
//			.and()
//			.withUser("admin").password("{noop}1234").roles("MEMBER", "ADMIN")
//		;
	}
	
	@Bean
	public CustomUserDetailsService userDetailsService(){
		return new CustomUserDetailsService();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
//		return new PasswordEncoder() {
//			
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword) {
//
//				return rawPassword.toString().equals(encodedPassword);
//			}
//			
//			@Override
//			public String encode(CharSequence rawPassword) {
//
//				return rawPassword.toString();
//			}
//		};
		return new BCryptPasswordEncoder();
	}
}
