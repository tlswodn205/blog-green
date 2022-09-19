package site.metacoding.red.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.service.HeartsService;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.request.hearts.HeartsJsp;
import site.metacoding.red.web.dto.request.hearts.HeartsReqDto;
import site.metacoding.red.web.dto.request.hearts.HeartsRespDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsService boardsService;
	private final HeartsService heartsService;

	/***
	 * 
	 *     인증과 권한 체크는 지금 하지 마세요!!
	 */

	@PutMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id,@RequestBody UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		return new CMRespDto<>(1,"게시글 수정 완료", null);
	}
	

	private final UsersService usersService;
	@GetMapping("/boards/{qty}/dummy")
	public String 더미생성기(@PathVariable Integer qty) {
		Users user = usersService.회원정보보기(1);
		for (int i = 0; qty>i; i++) {
			WriteDto writeDto = new WriteDto((i+1)+"번째 더미입니다.", "더미데이터 입니다.");
			boardsService.게시글쓰기(writeDto, user);
		}
		return "redirect:/";
	}

	@GetMapping("/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boardsPS = boardsService.게시글상세보기(id);
		model.addAttribute("boards", boardsPS);
		return "boards/updateForm";
	}

	@DeleteMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteBoards(@PathVariable Integer id) {
		boardsService.게시글삭제하기(id);
		heartsService.글삭제에따른좋아요삭제(id);
		return new CMRespDto<>(1,"리퀘스트 되었습니다.", null);
	}

	@PostMapping("/boards")
	public @ResponseBody CMRespDto<?> writeBoards(@RequestBody WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		System.out.println(writeDto.getTitle());
		boardsService.게시글쓰기(writeDto, principal);
		return new CMRespDto<>(1,"글쓰기 성공", null);
	}

	@GetMapping({ "/", "/boards" })
	public String getBoardList(Model model, Integer page, String keyword) { // 0 -> 0, 1->10, 2->20
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingDto", pagingDto);
		
		Map<String, Object> referer = new HashMap<>();

		referer.put("page", pagingDto.getCurrentPage());
		referer.put("keyword", pagingDto.getKeyword());
		session.setAttribute("referer", referer);		return "boards/main";
	}
	

	@PostMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> changeHeart(@PathVariable Integer id, @RequestBody HeartsJsp heartsJsp) {
		System.out.println(heartsJsp.getId());
		System.out.println(heartsJsp.isMyHeart());
		if(heartsJsp.isMyHeart()) {
			HeartsReqDto heartReqDto = new HeartsReqDto(heartsJsp.getUsersId(), id);
			heartsService.좋아요추가(heartReqDto);
		}else{
			heartsService.좋아요삭제(heartsJsp.getId());
		}
		
		return new CMRespDto<>(1,"글쓰기 성공", null);
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		Users principal = (Users) session.getAttribute("principal");
		HeartsReqDto heartsReqDto = new HeartsReqDto();
		if (principal ==null) {
			heartsReqDto.setUsersId(0);
			heartsReqDto.setBoardsId(id);
		}else {
			heartsReqDto.setUsersId(principal.getId());
			heartsReqDto.setBoardsId(id);
		}

		
		HeartsRespDto a = heartsService.좋아요세부사항(heartsReqDto);
		System.out.println(a.getId());
		
		System.out.println(a.isMyHeart());
		model.addAttribute("boards", boardsService.게시글상세보기(id));

		model.addAttribute("heartsRespDto", heartsService.좋아요세부사항(heartsReqDto));
		return "boards/detail";
	}

	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		return "boards/writeForm";
//		if (principal == null) {
//			return "redirect:/loginForm";
//		}
	}
}

