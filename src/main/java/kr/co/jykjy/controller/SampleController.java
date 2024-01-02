package kr.co.jykjy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.jykjy.domain.SampleVo;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	@GetMapping("all")
	public void all(){
		log.info("all");
	}
	@GetMapping("member")
	@PreAuthorize("hasRole('MEMBER')")
	public void member(){
		log.info("member");
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admin")
	public void admin(){
		log.info("admin");
	}
////	@ResponseBody : 
//	@GetMapping(value="getText", produces="text/plain; charset=utf-8")
//	public String getText(){
//		log.info(MediaType.TEXT_PLAIN_VALUE);
//		return "�븞�뀞�븯�꽭�슂";
//	}
//	
//	@GetMapping(value="getSample")
//	public SampleVo getSample(){
//		return new SampleVo(112, "�뒪��", "濡쒕뱶");
//	}
//	
//	@GetMapping("getList")
//	public List<SampleVo> getList(){
//		return IntStream.rangeClosed(1, 10).mapToObj(i -> new SampleVo(i, i+"First",i+"Last")).collect(Collectors.toList());
//	}
//	
//	@GetMapping("getMap")
//	public Map<?, ?> getMap(){
//		Map<String, SampleVo> map = new HashMap<>();
//		map.put("firstData", new SampleVo(111, "洹몃（�듃", "二쇰땲�뼱"));
//		return map;
//	}
//	
//	@GetMapping("check")
//	public ResponseEntity<SampleVo> check(Double height, Double weight){
//		SampleVo sampleVo = new SampleVo(0, height + "", weight + "");
//		if(height < 150){
////			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(sampleVo);
//			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
//		}
//		else{
//			return ResponseEntity.status(HttpStatus.OK).body(sampleVo);
//		}
//	}
	
	// function() {} 
	// () => {}
	
	// js array
	// [1,2,3,4,5].map(function(){})
	// [1,2,3,4,5].map(()=>{})
	
	// Runnable run() {}
	// () -> {} : runnable
	// (p) -> {} : consumer
	// () -> {return}  // 諛섑솚 �뿬遺� : supplier
	// (p) -> {return} : function
	// (p) -> {return (boolean)} : predicate
	
	// Generic Lambda Stream
	
	// @ResponseBody @Controller�쓽 硫붿꽌�뱶 諛섑솚 �닚�닔 媛앹껜�씪 寃쎌슦 json, xml
	// @RequestBody 
	
//	@GetMapping({"product/{cat}/{productid}", "product/{cat}"})
//	public String[]	getPath(@PathVariable("cat") String cat, @PathVariable(value="productid", required=false) Optional<Integer> pid){
//		return new String[]	 {"category:" + cat, "productid:" + pid.orElseGet(()->10)};
//	}
//	
//	@PostMapping("post")
//	public SampleVo post(@RequestBody SampleVo sampleVo){
//		log.info(sampleVo);
//		return sampleVo;
//	}
	
}
