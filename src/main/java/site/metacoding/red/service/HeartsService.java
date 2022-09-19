package site.metacoding.red.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.hearts.HeartsDao;
import site.metacoding.red.web.dto.request.hearts.HeartsReqDto;
import site.metacoding.red.web.dto.request.hearts.HeartsRespDto;

@RequiredArgsConstructor
@Service
public class HeartsService {
	public final HeartsDao heartsDao;
	
	public void 좋아요추가(HeartsReqDto heartsReqDto) {
		heartsDao.insert(heartsReqDto);
	}
	
	public void 좋아요삭제(int id) {
		heartsDao.deleteById(id);
	}
	
	public HeartsRespDto 좋아요세부사항(HeartsReqDto heartsReqDto) {
		HeartsRespDto heartsRespDto = heartsDao.findByBoardsId(heartsReqDto);
		return heartsRespDto;
	}
	
	public void 계정삭제에따른좋아요삭제(int usersId) {
		heartsDao.deleteByUsersId(usersId);
		System.out.println(usersId);
	}
	
	public void 글삭제에따른좋아요삭제(int boardsId) {
		heartsDao.deleteByBoardsId(boardsId);
	}
}
