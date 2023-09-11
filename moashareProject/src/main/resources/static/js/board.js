$(document).on('click', '#btnSave', function(e) {

	e.preventDefault();
	console.log("버튼눌림");
	var title = $("#title").val();
	console.log("title : " + title);
	var content = $("#content").val();
	console.log("content : " + content);
	
	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../boardApi/writeOk',
		type: 'post',
		data: {
			title : title,
			content : content
		},
		success: function(response) {
			console.log(response);
			alert("새 글 작성이 완료되었습니다.")
			location.href="/board"
		},
		error: function() {
			alert("서버요청실패");
		}
	})
	

});
$(document).on('click', '#btnUpdate', function(e) {
	e.preventDefault();
	console.log("버튼눌림");
	var id = $("#id").val();
	var title = $("#title").val();
	console.log("title : " + title);
	var content = $("#content").val();
	console.log("content : " + content);
	
	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../../boardApi/board/'+id,
		type: 'PUT',
		data: {
			title : title,
			content : content,
		},
		success: function(response) {
			console.log(response);
			alert("글 수정이 완료되었습니다.")
			location.href="/board"
		},
		error: function() {
			alert("서버요청실패");
		}
	})
});


$(document).on('click', '#btnList', function(e) {

	e.preventDefault();
	location.href = "/board";

});



