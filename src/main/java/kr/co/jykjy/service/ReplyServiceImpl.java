package kr.co.jykjy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.ReplyPageDTO;
import kr.co.jykjy.domain.ReplyVo;
import kr.co.jykjy.mapper.BoardMapper;
import kr.co.jykjy.mapper.ReplyMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{
	private BoardMapper boardMapper;
	private ReplyMapper mapper;

	@Override
	@Transactional
	public int register(ReplyVo vo) {
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		return mapper.insert(vo);
	}

	@Override
	public ReplyVo get(Long rno) {
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVo vo) {
		
		return mapper.update(vo);
	}

	@Override
	@Transactional
	public int remove(Long rno) {
		boardMapper.updateReplyCnt(get(rno).getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVo> getList(Criteria criteria, Long bno) {
		
		return mapper.getListWithPaging(criteria, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria criteria, Long bno) {
		return new ReplyPageDTO(mapper.getTotal(criteria, bno), mapper.getListWithPaging(criteria, bno));
	}
	
	
	
}
