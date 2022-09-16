package site.metacoding.red.web.dto.request.boards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WriteDto {
	private String title;
	private String content;
	


	public Boards toEntity(Integer usersId) {
		Boards boards = new Boards(this.title, this.content, usersId);
		return boards;
	}
}
