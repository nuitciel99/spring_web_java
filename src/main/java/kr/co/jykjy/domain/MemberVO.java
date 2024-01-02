package kr.co.jykjy.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String id;
	private String pw;
	private String username;
	private boolean enabled;
	
	private Date regdate;
	private Date updatedate;
	private List<AuthVO> auths;
	
}
