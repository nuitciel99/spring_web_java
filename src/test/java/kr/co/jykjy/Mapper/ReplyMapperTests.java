package kr.co.jykjy.Mapper;

import static org.junit.Assert.assertNotNull;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.ReplyVo;
import kr.co.jykjy.mapper.ReplyMapper;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class ReplyMapperTests {
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void testMapper(){
		log.info(replyMapper);
		assertNotNull("replyMapper가 널", replyMapper);
	}
	
	@Test
	public void testCreate(){
		long[] bnoArr = {194, 193, 192, 191, 190};
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVo vo = new ReplyVo();
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글테스트" + i);
			vo.setReplyer("replyer" + i);
			replyMapper.insert(vo);
		});
	}
	
	@Test
	public void testGetList(){
		Criteria cri = new Criteria();
		cri.setPageNum(2);
		cri.setAmount(5);
		replyMapper.getListWithPaging(cri, 194L).forEach(reply->log.info(reply));
	}
	
	@Test
	public void testRead(){
		log.info(replyMapper.read(5L));
	}
	
	@Test
	public void testDelete(){
		replyMapper.delete(2L);
	}
	@Test
	public void testUpdate(){
		ReplyVo vo = replyMapper.read(1L);
		vo.setReply("수정된 내용");
		replyMapper.update(vo);
	}
	
	@Test
	public void testGetTotal(){
		log.info(replyMapper.getTotal(new Criteria(), 194L));
	}
}
