package site.metacoding.red.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class CMRespDto<T> {
	private Integer code; //1 정상, -1 실패
	private String msg; // 실패의 이유, 성공한이유
	private T data;
}
