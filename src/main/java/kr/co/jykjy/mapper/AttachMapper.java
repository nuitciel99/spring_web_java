package kr.co.jykjy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.co.jykjy.domain.AttachVo;

public interface AttachMapper {
	
	@Insert("insert into tbl_attach values (#{uuid}, #{path}, #{origin}, #{image}, #{bno}, #{rno})")
	void insert(AttachVo vo);
	
	@Delete("delete from tbl_attach where uuid = #{uuid}")
	void delete(String uuid);
	
	@Delete("delete from tbl_attach where bno = #{bno}")
	void deleteAll(Long bno);
	
	@Select("select * from tbl_attach where bno = #{bno}")
	List<AttachVo> findByBno(Long bno);
	
	@Select("select * from tbl_attach where path = date_format(adddate(now(), -1), '%Y/%m/%d')")
	List<AttachVo> getOldFiles();
}
