package kr.co.jykjy.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.config.SecurityConfig;
import kr.co.jykjy.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class, SecurityConfig.class})
@Log4j
public class PasswordEncoderTests {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private MemberMapper memberMapper;
	
	@Test
	public void testExist(){
		log.info(passwordEncoder);
	}
	
	@Test
	public void testStr(){
		String raw = "1234";
		String encoded = passwordEncoder.encode(raw);
		
		log.info(raw);
		log.info(encoded);
		
		log.info(raw.equals(encoded));
		// DB에 들어가야함 밑에가 : 탈퇴 회원의 아이디는 인코드하고 tbl_outMember에 insert
		log.info(passwordEncoder.matches(raw, encoded));
	}
	
	@Test
	public void updatePwd(){
		memberMapper.updatePwd("member", passwordEncoder.encode("1234"));
		memberMapper.updatePwd("admin", passwordEncoder.encode("1234"));
	}
}
