$(document).on('click', '#deleteBoard', function(e) {
	e.preventDefault();
	console.log("버튼눌림");
	var id = $("#id").val();
	console.log("id : " + id);

	// ajax 호출 시 default가 비동기 호출, 아래 코드 실행 가능
	$.ajax({
		url: '../boardApi/board/'+id,
		type: 'DELETE',
		data: {
			JSON
		},
		success: function(response) {
			console.log(response);
			alert("삭제가 완료되었습니다.")
			history.back();
		},
		error: function() {
			alert("서버요청실패");
		}
	})
	

});

