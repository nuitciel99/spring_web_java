package kr.co.jykjy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.jykjy.config.RootConfig;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootConfig.class)
@Log4j
public class SServiceTests {

	@Autowired
	private SService service;
	
	@Test
	public void testDo(){
//		long value = 1234;
		long value = Long.MAX_VALUE;
		service.doSomething(value);
	}
}
