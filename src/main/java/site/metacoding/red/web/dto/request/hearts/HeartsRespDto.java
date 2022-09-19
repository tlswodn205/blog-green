package site.metacoding.red.web.dto.request.hearts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartsRespDto {
	private int id;
	private int totalHeart;
	private boolean myHeart;
}
