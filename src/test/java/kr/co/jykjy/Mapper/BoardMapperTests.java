package kr.co.jykjy.Mapper;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.domain.BoardVo;
import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.mapper.BoardMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class BoardMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	@Test
	public void testGetList(){
		boardMapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testCriteriaGetTypeArr(){
		Criteria criteria = new Criteria(1, 10, "T,C,W", "제목");
		// given
		
		// when // then 
		
		
		// expect
		int expect = criteria.getTypeArr().length;
		int result = 3;
		assertEquals(expect, result);
	}
	
	@Test
	public void testGetListWithPaging(){
		Criteria criteria = new Criteria(1, 10, "T,C,W", "제목");
//		Criteria criteria = new Criteria();
		log.info(criteria);
//		criteria.getTypeArr().length
		boardMapper.getListWithPaging(criteria).forEach(log::info);
	}
	
	@Test
	public void testInsert(){
		BoardVo vo = new BoardVo();
		vo.setTitle("새 제목");
		vo.setContent("새 내용");
		vo.setWriter("작성자");
		
		boardMapper.insert(vo);
		log.info(vo);
	}
	
	@Test
	public void testInsertSelectKey(){
		BoardVo vo = new BoardVo();
		vo.setTitle("새 글 key");
		vo.setContent("새 내용 key");
		vo.setWriter("작성자 key");
		
		boardMapper.insertSelectKey(vo);
		log.info(vo);
	}
	@Test
	public void testRead(){
		BoardVo vo = new BoardVo();
		boardMapper.read(5L);
		log.info(vo);
	}
	
	@Test
	public void testRead2(){
		// {"bno" : "1234"}
		Map<?, ?> vo = boardMapper.read2(189L);
		log.info(vo);
		log.info(vo.get("bno"));
	}
	
	@Test
	public void testDelete(){
		BoardVo vo = new BoardVo();
		boardMapper.delete(3L);
		log.info(vo);
	}
	@Test
	public void testUpdate(){
		BoardVo vo = new BoardVo();
		vo.setBno(4L);
		vo.setTitle("수정 제목");
		vo.setContent("수정 내용");
		vo.setWriter("수정 작성자");
		
		boardMapper.update(vo);
		vo = boardMapper.read(4L);
		log.info(vo);
	}
	
	@Test
	public void testSearch(){
		Map<String, String> map = new HashMap<>();
		map.put("T", "제목");
		map.put("C", "수정");
		
		boardMapper.testSearch(map).forEach(i->log.info(i));
	}
	
	@Test
	public void testGetTotal(){
		Criteria criteria = new Criteria();
//		"abcd".contains("a");
		criteria.setType("T,C");
		criteria.setKeyword("제목");
		log.info(boardMapper.getTotal(criteria));
	}
	
	@Test
	public void testUpdateReplyCnt(){
		boardMapper.updateReplyCnt(194L, -1);
	}
}
