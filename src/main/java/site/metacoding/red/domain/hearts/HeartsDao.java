package site.metacoding.red.domain.hearts;

import site.metacoding.red.web.dto.request.hearts.HeartsReqDto;
import site.metacoding.red.web.dto.request.hearts.HeartsRespDto;

public interface HeartsDao {
	public HeartsRespDto findByBoardsId(HeartsReqDto heartsReqDto);
	public void insert(HeartsReqDto heartsReqDto);
	public void deleteById(int id);
	public void deleteByBoardsId(int boardsId);
	public void deleteByUsersId(int usersId);
}
