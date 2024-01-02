package kr.co.jykjy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import kr.co.jykjy.domain.AuthVO;
import kr.co.jykjy.domain.MemberVO;

public interface MemberMapper {
	
	@Update("update tbl_member set pw = #{password} where id = #{username}")
	int updatePwd(@Param("username") String username, @Param("password") String password);
	
	MemberVO read(String id);
	
	@Insert("insert into tbl_member (id, pw, username) values (#{id}, #{pw}, #{username})")
	int insertMember(MemberVO memberVO);
	
	@Insert("insert into tbl_auth values (#{id}, #{auth})")
	int insertAuth(AuthVO authVO);
	
}
