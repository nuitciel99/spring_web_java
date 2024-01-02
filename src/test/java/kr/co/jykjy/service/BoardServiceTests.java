package kr.co.jykjy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.domain.BoardVo;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class BoardServiceTests {
	
	@Setter(onMethod_ = {@Autowired})
	private BoardService boardService;
	
	@Test
	public void testExist(){
		boardService.getList(null).forEach(board -> log.info(board));
	}
	
	@Test
	public void testInsert(){
		BoardVo vo = new BoardVo();
		vo.setTitle("제목");
		vo.setContent("내용");
		vo.setWriter("작성자");
		
		boardService.register(vo);
		log.info(vo);
	}
	
	@Test
	public void testGet(){
		log.info(boardService.get(1L));
	}
	@Test
	public void testUpdate(){
		BoardVo vo = new BoardVo();
		vo.setBno(5L);
		vo.setTitle("수정 제목");
		vo.setContent("수정 내용");
		vo.setWriter("수정 작성자");
		
		boardService.modify(vo);
		
		vo = boardService.get(5L);
	}
}
