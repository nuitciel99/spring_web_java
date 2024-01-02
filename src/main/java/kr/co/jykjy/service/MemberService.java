package kr.co.jykjy.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.jykjy.domain.AuthVO;
import kr.co.jykjy.domain.MemberVO;
import kr.co.jykjy.mapper.MemberMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MemberService {
	private MemberMapper memberMapper;
	private BCryptPasswordEncoder passwordEncoder;
	
	public void signin(MemberVO vo){
		// 1. vo 비밀번호 암호화
		vo.setPw(passwordEncoder.encode(vo.getPw()));
		
		// 2. tbl_member insert
		memberMapper.insertMember(vo);
		
		// 3. tbl_auth insert ROLE_MEMBER
		AuthVO authVO = new AuthVO();
		authVO.setId(vo.getId());
		authVO.setAuth("ROLE_MEMBER");
		memberMapper.insertAuth(authVO);
	}
}
