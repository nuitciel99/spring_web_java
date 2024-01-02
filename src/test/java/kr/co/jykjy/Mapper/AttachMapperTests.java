package kr.co.jykjy.Mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.domain.AttachVo;
import kr.co.jykjy.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class AttachMapperTests {
	
	@Autowired
	private AttachMapper mapper;
	
	@Test
	public void testInsert(){
		AttachVo vo = AttachVo.builder().uuid("3").image(true).path("2").origin("3").bno(194L).build();
		mapper.insert(vo);
	}
	
	@Test
	public void testSelect(){
		mapper.findByBno(194L).forEach(a->log.info(a));
	}
	@Test
	public void testDelete(){
		mapper.delete("3");
	}
}
