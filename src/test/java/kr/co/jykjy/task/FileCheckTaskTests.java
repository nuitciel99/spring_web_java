package kr.co.jykjy.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class FileCheckTaskTests {
	
	@Autowired
	private FileCheckTask fileCheckTask;
	
	@Test
	public void testCheck(){
		fileCheckTask.check();
	}
	
	@Test
	public void testRemoveList(){
		List<File> list1 = new ArrayList<>(Arrays.asList(new File("c:/upload"), new File("c:/Windows"), new File("c:/kjy")));
		List<File> list2 = new ArrayList<>(Arrays.asList(new File("c:/upload"), new File("c:/Windows")));
		
		list1.removeAll(list2);
		log.info(list1);
	}
}
