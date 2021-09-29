package com.cos.blogapp2.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final HttpSession session;
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) { // title=제목&content=내용
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("principal");
		board.setUser(principal);
		boardRepository.save(board);
		return "redirect:/";
	}
	
	
	@GetMapping("/board")
	public String list(Model model) {	
		
		List<Board> boardsEntity =  boardRepository.findAll();	
		model.addAttribute("boardsEntity", boardsEntity);
		
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {	
		
		Board boardEntity =  boardRepository.findById(id).get(); // get() 옵셔널안에 있는 걸 그대로 꺼내오는것
		
		model.addAttribute("boardEntity", boardEntity);
		
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		return "board/saveForm";
	}
	
}


