package kr.co.jykjy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.config.ServletConfig;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class, ServletConfig.class})
@WebAppConfiguration
@Log4j
public class BoardControllerTests {
	
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	@Test
	public void testList() throws Exception{
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).andReturn();
		Map<String, Object> model = mr.getModelAndView().getModel();
		log.info(model.get("list"));
	}
	@Test
	public void testRegister() throws Exception{
		MvcResult mr = mockMvc.perform(
				
				MockMvcRequestBuilders.post("/board/register")
				.param("title", "제목")
				.param("content", "내용")
				.param("writer", "지은이")
				).andReturn();
				;
		String viewName = mr.getModelAndView().getViewName();
	}
	@Test
	public void testGet() throws Exception{
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get("/board/get").param("bno", "1")).andReturn();
		Map<String, Object> model = mr.getModelAndView().getModel();
		log.info(model.get("board"));
	}
	@Test
	public void testModify() throws Exception{
		MvcResult mr = mockMvc.perform(
				MockMvcRequestBuilders.post("/board/modify")
				.param("title", "제목 수정")
				.param("content", "수정")
				.param("writer", "종욱이")
				.param("bno", "11")
				).andReturn();
				;
		String viewName = mr.getModelAndView().getViewName();
	}
	@Test
	public void testRemove() throws Exception{
		MvcResult mr = mockMvc.perform(
				MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "")
				).andReturn();
				;
		String viewName = mr.getModelAndView().getViewName();
	}
	
	
	
}
