package kr.co.jykjy.service;

import java.util.List;

import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.ReplyPageDTO;
import kr.co.jykjy.domain.ReplyVo;

public interface ReplyService {
	int register(ReplyVo vo);
	
	ReplyVo get(Long rno);
	
	int modify(ReplyVo vo);
	
	int remove(Long rno);
	
	List<ReplyVo> getList(Criteria criteria, Long bno);

	ReplyPageDTO getListPage(Criteria criteria, Long bno);
	
	
}
