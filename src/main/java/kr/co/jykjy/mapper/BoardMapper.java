package kr.co.jykjy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.co.jykjy.domain.BoardVo;
import kr.co.jykjy.domain.Criteria;

public interface BoardMapper {
	
//	@Select("select * from tbl_board where bno > 0")
	List<BoardVo> getList();
	
	List<BoardVo> getListWithPaging(Criteria criteria);
	
	int getTotal(Criteria criteria);
	
	void insert(BoardVo boardVo);
	
	public void insertSelectKey(BoardVo boardVo);
	
	BoardVo read(Long bno);

	@Select("select * from tbl_board where bno = #{bno}")
	Map<String, Object> read2(Long bno);
	
	int delete(Long bno);
	
	int update(BoardVo boardVo);
	
	List<BoardVo> testSearch(@Param("map") Map<String, String> map);
	
	void updateReplyCnt(@Param("bno")Long bno, @Param("amount") int amount);

}
