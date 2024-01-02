package kr.co.jykjy.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import kr.co.jykjy.config.RootConfig;
import kr.co.jykjy.config.ServletConfig;
import kr.co.jykjy.domain.SampleVo;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class, ServletConfig.class})
@WebAppConfiguration
@Log4j
public class SampleControllerTests {
	
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	@Autowired
	private Gson gson;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testToJsonStr(){
		SampleVo vo = new SampleVo(11, "길동", "홍");
		String jsonStr = gson.toJson(vo);
		log.info(jsonStr);
	}
	
	@Test
	public void testProduct() throws Exception{
		MvcResult mr = mockMvc.perform(MockMvcRequestBuilders.get("/sample/product/bags/1234.json")).andReturn();
		log.info(mr);
		String content = mr.getResponse().getContentAsString();
		log.info(content);
	}

	@Test
	public void testFromJsonStr(){
		String str = "{\"mno\":11,\"firstName\":\"길동\"}";
		SampleVo vo = gson.fromJson(str, SampleVo.class);
		log.info(vo);
	}
	
	@Test
    public void testSample() throws Exception {
		SampleVo vo = new SampleVo(11, "길동", "홍");
		String jsonStr = gson.toJson(vo);
		
        String content = mockMvc.perform(MockMvcRequestBuilders
                .post("/sample/post.json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr)
                ).andReturn().getResponse().getContentAsString();
        
        log.info(content);
        SampleVo result = gson.fromJson(content, SampleVo.class);
        
        // .equals hashcode
        // equals : 동등
        // same : 동일
        assertEquals(vo, result);
        assertNotSame(vo, result);

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
