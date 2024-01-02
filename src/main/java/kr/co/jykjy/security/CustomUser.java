package kr.co.jykjy.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.co.jykjy.domain.MemberVO;
import lombok.Getter;
// principal.member.username
public class CustomUser extends User{
	@Getter
	private MemberVO member;

	
	public CustomUser(MemberVO vo) {
		super(vo.getId(), vo.getPw(), vo.getAuths().stream().map(a -> new SimpleGrantedAuthority(a.getAuth())).collect(Collectors.toList()));
		this.member = vo;
	}


	@Override
	public String toString() {

		return super.toString() + ":: CustomUser [member=]" + member + "]";
	}
	
	

}
