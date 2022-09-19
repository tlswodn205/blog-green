<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<input id = "page" type="hidden" value="${sessionScope.referer.page}">
<input id = "keyword" type="hidden" value="${sessionScope.referer.keyword}">
<div class="container">
	<br /> <br />

		<div class="d-flex">
			<input id = "heartsId" type="hidden" value="${heartsRespDto.id}">
			<input id = "usersId" type="hidden" value="${principal.id}">
			<input id = "boardsId" type="hidden" value="${boards.id}">
			<a href="/boards/${boards.id}/updateForm" class="btn btn-warning">수정하러가기</a>

			<form>
				<button id="delete" class="btn btn-danger">삭제</button>
			</form>
		</div>


	<br />
	<h3>${boards.title}</h3>
	<div class="d-flex flex-row justify-content-between">
	
		<h6>${boards.createdAt}</h6>
		<div>좋아요수 : <span id="totalHeart">${heartsRespDto.totalHeart} <i id="iconHeart" class="${heartsRespDto.myHeart ? 'fa-solid' : 'fa-regular'} fa-heart" style="color: ${heartsRespDto.myHeart ? 'red' : 'black'};"></i></span> </div>
	</div>
	<hr />

	<div>${boards.content}</div>


</div>

<script>
	$("#delete").click(()=>{
		remove();
	});
	
	function remove(){
		let id = $("#boardsId").val();
		let page = $("#page").val();
		let keyword = $("#keyword").val();
		
		alert(keyword);
		alert(page);
	
		$.ajax("/boards/"+id, {
			type: "DELETE",
			dataType: "json"
		}).done((res) => {
			if (res.code == 1) {
				alert("게시글 삭제 완료");
				console.log(res);
				location.href = document.referer;
				//location.href = "/";
				location.href = "/?page="+page+"&keyword="+keyword;
			} else {
				alert("게시글 삭제 실패");
			}
		});
	}
		
	
	

</script>

<script>
	$("#iconHeart").click(()=>{
		let check = $("#iconHeart").hasClass("fa-regular");
		
		change(check);
	});
	
	
	function change(check) {
		let id = $("#boardsId").val();
		
		let data = {
			id : $("#heartsId").val(),	
			usersId : $("#usersId").val(),
			myHeart : check
		};
		
		$.ajax("/boards/" + id, {
			type: "POST",
			dataType: "json",  //응답 데이터
			data: JSON.stringify(data), //http body에 들고갈 요청 데이터
			headers: {	//http header에 들고갈 요청데이터
				"Content-Type": "application/json"
			}
		}).done((res) => {
			location.reload($("#totalHeart"));
		});
	}
		
</script>
<%@ include file="../layout/footer.jsp"%>

