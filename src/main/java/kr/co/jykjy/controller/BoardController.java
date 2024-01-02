package kr.co.jykjy.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.jykjy.domain.AttachDTO;
import kr.co.jykjy.domain.AttachVo;
import kr.co.jykjy.domain.BoardVo;
import kr.co.jykjy.domain.Criteria;
import kr.co.jykjy.domain.PageDto;
import kr.co.jykjy.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	private BoardService boardService;
	
	@GetMapping("/list")
	public void list(Model model, Criteria criteria){
		model.addAttribute("list", boardService.getList(criteria));
		model.addAttribute("page", new PageDto(criteria, boardService.getTotal(criteria)));
	}
	
	@GetMapping("/register")
	@PreAuthorize("authenticated")
	public void register(Criteria criteria){
		
	}
	@PostMapping("/register")
	@PreAuthorize("authenticated")
	public String register(BoardVo vo, RedirectAttributes rttr, Criteria criteria){
		// ?title=제목&content=내용&writer=작성자&attachs%5B0%5D.uuid=uuid&attachs%5B.path=path&attachs%5B0%5D.image=false&attachs%5B0%5D.origin=원본
		log.info(vo);
		boardService.register(vo);
		log.info(vo);
		rttr.addFlashAttribute("result", vo.getBno());
		return "redirect:/board/list" + criteria.getLink();
	}
	
	@GetMapping("get")
	public void get(Long bno, Model model, Criteria criteria){
		model.addAttribute("board", boardService.get(bno));
	}
	
	@PreAuthorize("authenticated")
	@GetMapping("modify")
	public void modify(Long bno, Model model, Criteria criteria){
		model.addAttribute("board", boardService.get(bno));
	}
	
	@PreAuthorize("authenticated && principal.username == #vo.writer")
	@PostMapping("modify")
	public String modify(BoardVo vo, RedirectAttributes rttr, Criteria criteria){
		log.info(vo);
		log.info(criteria);
		if(boardService.modify(vo)){
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + criteria.getLink();
	}
	
	@PreAuthorize("authenticated && principal.username == #writer")
	@PostMapping("remove")
	public String remove(Long bno, RedirectAttributes rttr, Criteria criteria, String writer){
		log.info(bno);
		// 첨부 파일 목록 조회
		List<AttachDTO> list = getAttachs(bno);
		if(boardService.remove(bno)){
			list.forEach(dto -> {
				dto.toFile().delete();
				dto.toFile(dto.isImage()).delete();
			});
			rttr.addFlashAttribute("result", "success");
			// 첨부 파일 삭제
		}
		return "redirect:/board/list" + criteria.getLink();
	}
	
	@GetMapping("getAttachs")
	@ResponseBody
	public List<AttachDTO> getAttachs(Long bno){
		return boardService.getAttachs(bno);
	}
}
