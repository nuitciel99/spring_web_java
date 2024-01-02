package kr.co.jykjy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.jykjy.domain.AttachDTO;
import kr.co.jykjy.domain.AttachVo;
import kr.co.jykjy.domain.BoardVo;
import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.mapper.AttachMapper;
import kr.co.jykjy.mapper.BoardMapper;
import kr.co.jykjy.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	private AttachMapper attachMapper;
	private ReplyMapper replyMapper;

	@Override
	@Transactional
	public void register(BoardVo boardVo) {
		boardMapper.insertSelectKey(boardVo);
//		boardVo.getBno();
		boardVo.getAttachs().forEach(attach -> {
			attach.setBno(boardVo.getBno());
			attachMapper.insert(attach);
		});
	}

	@Override
	public BoardVo get(Long bno) {
		// List<AttachVO> list = attachMapper.getList(bno);
		return boardMapper.read(bno);
	}

	@Override
	public boolean modify(BoardVo boardVo) {
		boolean b = boardMapper.update(boardVo) > 0;
		// 게시글 수정
		if(b){
			// 첨부 파일 삭제
			attachMapper.deleteAll(boardVo.getBno());
			
			// 첨부 파일 등록 
			boardVo.getAttachs().forEach(attach -> {
				attach.setBno(boardVo.getBno());
				attachMapper.insert(attach);
			});
		}
		
		return b;
	}
	
	@Override
	@Transactional
	public boolean remove(Long bno) {
		// 
		replyMapper.deleteAll(bno);
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) > 0;
	}

	@Override
	public List<BoardVo> getList(Criteria criteria) {
		return boardMapper.getListWithPaging(criteria);
	}

	@Override
	public int getTotal(Criteria criteria) {
		return boardMapper.getTotal(criteria);
	}

	@Override
	public List<AttachDTO> getAttachs(Long bno) {
		// List<AttachVO> >> List<AttachDTO>
//		return attachMapper.findByBno(bno).stream().map(AttachDTO::new).collect(Collectors.toList());
		return attachMapper.findByBno(bno).stream().map(vo -> new AttachDTO(vo)).collect(Collectors.toList());
	}
	
	

}
