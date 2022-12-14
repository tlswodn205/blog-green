let isUsernameSameCheck = false;

// 회원가입
$("#btnJoin").click(() => {
	join();
});

$("#btnUsernameSameCheck").click(() => {
	checkUsername();
});

$("#btnLogin").click(() => {
	login();
});

$("#btnDelete").click(() => {
	resign();
});

$("#btnUpdate").click(() => {
	update();
});


function join() {
	if (isUsernameSameCheck == false) {
		alert("유저네임 중복 체크를 진행해주세요");
		return;
	}

	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};



	$.ajax("/join", {
		type: "POST",
		dataType: "json",  //응답 데이터
		data: JSON.stringify(data), //http body에 들고갈 요청 데이터
		headers: {	//http header에 들고갈 요청데이터
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			console.log(res);
			location.href = "/";
		}
	});
}

function checkUsername() {
	let username = $("#username").val();

	$.ajax("/users/usernameSameCheck?username=" + username, {
		type: "GET",
		dataType: "json",
		async: true
	}).done((res) => {
		console.log(res);
		if (res.code == 1) { // 통신 성공
			if (res.data == false) {
				alert("아이디가 중복되지 않았습니다.");
				isUsernameSameCheck = true;
			} else {
				alert("아이디가 중복되었어요. 다른 아이디를 사용해주세요.");
				isUsernameSameCheck = false;
				$("#username").val("");
			}
		}
	});
}

function myTest(){
	let body = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember : $('#remember').prop("checked")
		};
	console.log(body);
}

function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember : $('#remember').prop("checked")
	};

	alert(data.username);

	$.ajax("/login", {
		type: "POST",
		dataType: "json", //응답 데이터
		data: JSON.stringify(data), //http body에 들고갈 요청 데이터
		headers: {	//http header에 들고갈 요청데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			console.log(res);
			location.href = "/";
		} else {
			alert("로그인 실패, 아이디 패스워드를 확인해주세요");
		}
	});
}

function resign() {
	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "DELETE",
		dataType: "json" //응답 데이터
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴완료");
			console.log(res);
			location.href = "/";
		} else {
			alert("회원탈퇴 실패");
		}
	});
}

function update(){
	alert("login 함수 실행")
		let data = {
		password: $("#password").val(),
		email: $("#email").val()
	};

	alert(data.password);

	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "Put",
		dataType: "json", //응답 데이터
		data: JSON.stringify(data), //http body에 들고갈 요청 데이터
		headers: {	//http header에 들고갈 요청데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("회원수정완료");
			console.log(res);
			location.reload();
		} else {
			alert("업데이트 실패");
		}
	});
}
/**
 * 
 
 */