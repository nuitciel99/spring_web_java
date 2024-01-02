package kr.co.jykjy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.ReplyVo;

public interface ReplyMapper {
	
	int insert(ReplyVo vo);
	
	ReplyVo read(Long rno);
	
	int delete(Long rno);
	
	@Delete("delete from tbl_reply where bno = #{bno}")
	int deleteAll(Long bno);
	
	int update(ReplyVo vo);
	
	List<ReplyVo> getListWithPaging(@Param("cri") Criteria criteria, @Param("bno") Long bno);
	
	int getTotal(@Param("cri") Criteria criteria, @Param("bno") Long bno);

}
