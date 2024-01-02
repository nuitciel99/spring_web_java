package kr.co.jykjy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.jykjy.domain.MemberVO;
import kr.co.jykjy.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class CommonController {
	private MemberService memberService;
	
	@GetMapping("accessError")
	public void accessDenied(Authentication auth, Model model){
		log.info("access denied :: " + auth);
		model.addAttribute("msg", "Access Denied");
	}
	
	@GetMapping("/member/login")
	public void customLogin(String error, Model model){
		log.info(error);
		if(error != null){
			model.addAttribute("error", "login Error");
		}
	}
	
	@GetMapping("/member/signin")
	public void signin(){
		
	}
	@PostMapping("/member/signin")
	public String signin(MemberVO vo){
		memberService.signin(vo);
		return "redirect:/";
	}
}
