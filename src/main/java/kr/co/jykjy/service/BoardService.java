package kr.co.jykjy.service;

import java.util.List;

import kr.co.jykjy.domain.AttachDTO;
import kr.co.jykjy.domain.AttachVo;
import kr.co.jykjy.domain.BoardVo;
import kr.co.jykjy.domain.Criteria;

public interface BoardService {
	
	void register(BoardVo boardVo);
	
	BoardVo get(Long bno);
	
	boolean modify(BoardVo boardVo);
	
	boolean remove(Long bno);
	
	List<BoardVo> getList(Criteria criteria);
	
	int getTotal(Criteria criteria);
	
	List<AttachDTO> getAttachs(Long bno);
}
