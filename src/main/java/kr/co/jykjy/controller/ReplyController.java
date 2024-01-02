package kr.co.jykjy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.ReplyPageDTO;
import kr.co.jykjy.domain.ReplyVo;
import kr.co.jykjy.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("replies")
@AllArgsConstructor
@Log4j
public class ReplyController {
	private ReplyService service;
	
	@PreAuthorize("authenticated")
	@PostMapping
	public ResponseEntity<String> create(@RequestBody ReplyVo vo){
		log.info(vo);
		
		return service.register(vo) == 1 ? ResponseEntity.status(HttpStatus.OK).body("success") : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping({"pages/{bno}", "pages/{bno}/{page}"})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable Long bno, @PathVariable(required=false) Optional<Integer> page){
		Criteria criteria = new Criteria();
		criteria.setPageNum(page.orElseGet(() -> 1));
		
		log.info(criteria);
		
		return ResponseEntity.status(HttpStatus.OK).body(service.getListPage(criteria, bno));
	}
	
	@GetMapping("{rno}")
	public ReplyVo get(@PathVariable Long rno){
		return service.get(rno);
	}
	@PreAuthorize("authenticated")
	@DeleteMapping("{rno}")
	public ResponseEntity<String> remove(@PathVariable Long rno){
		return service.remove(rno) == 1 ? ResponseEntity.status(HttpStatus.OK).body("success") : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PreAuthorize("authenticated")
	@RequestMapping(value="{rno}", method={RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> modify(@RequestBody ReplyVo vo, @PathVariable Long rno){
		log.info(vo);
		return service.modify(vo) == 1 ? ResponseEntity.status(HttpStatus.OK).body("success") : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
